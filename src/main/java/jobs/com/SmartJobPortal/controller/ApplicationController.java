package jobs.com.SmartJobPortal.controller;

import jobs.com.SmartJobPortal.entity.Application;
import jobs.com.SmartJobPortal.entity.Job;
import jobs.com.SmartJobPortal.entity.JobPortalUser;
import jobs.com.SmartJobPortal.model.ApplicationStatus;
import jobs.com.SmartJobPortal.model.ApplyJobRequest;
import jobs.com.SmartJobPortal.model.JobApplicationResponse;
import jobs.com.SmartJobPortal.model.JobStatus;
import jobs.com.SmartJobPortal.repository.ApplicationRepo;
import jobs.com.SmartJobPortal.repository.JobRepo;
import jobs.com.SmartJobPortal.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/application")
public class ApplicationController {
    JobRepo jobRepo;
    ModelMapper modelMapper;
    UserRepo userRepo;
    ApplicationRepo applicationRepo;

    public ApplicationController(JobRepo jobRepo, ModelMapper modelMapper, UserRepo userRepo, ApplicationRepo applicationRepo) {
        this.jobRepo = jobRepo;
        this.modelMapper = modelMapper;
        this.userRepo = userRepo;
        this.applicationRepo = applicationRepo;
    }
    @PreAuthorize("hasRole('APPLICANT')")
    @PostMapping("/job/{id}")
    public ResponseEntity<?> applyForJob(@PathVariable Long id, @RequestBody ApplyJobRequest jobReq , Principal principal)
    {
        Job existJob=jobRepo.findById(id).orElse(null);

        if(existJob==null)
            return ResponseEntity.badRequest().body("Job Does not Exist");
        JobPortalUser user = userRepo.findByUsername(principal.getName())
                .orElseThrow( () -> new UsernameNotFoundException("User Not Found"));
        if(applicationRepo.findByJobAndJobSeeker(existJob, user).isPresent()){
            throw new RuntimeException ("You have already applied for this job");

        }
        if(existJob.getStatus() != JobStatus.OPEN){
            throw new IllegalStateException("Job is closed");
        }
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        Application application=modelMapper.map(jobReq, Application.class);
        application.setJob(existJob);
        application.setJobSeeker(user);
        Application saved = applicationRepo.save(application);

        JobApplicationResponse res=modelMapper.map(saved, JobApplicationResponse.class);
        res.setCompanyName(existJob.getCompany().getCompanyName());
        return ResponseEntity.status(HttpStatus.CREATED).body(res);

    }


    @PreAuthorize("hasRole('APPLICANT')")
    @GetMapping("/my")
    public ResponseEntity<?> getMyApplications(Principal principal)
    {

        JobPortalUser user = userRepo.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Application> applications =
                applicationRepo.findByJobSeeker(user);
        if(applications.isEmpty())
            return ResponseEntity.ok(applications);
        List<JobApplicationResponse> dto=new ArrayList<>();

        for(Application application:applications)
        {
            JobApplicationResponse response = modelMapper.map(application, JobApplicationResponse.class);
            response.setJobId(application.getJob().getId());
            response.setJobTitle(application.getJob().getTitle());
            response.setCompanyName(application.getJob().getCompany().getCompanyName());
            dto.add(response);
        }
            return ResponseEntity.status(HttpStatus.OK).body(dto);

    }

    @PreAuthorize("hasRole('RECRUITER')")
    @GetMapping("/{applicationId}")
    public ResponseEntity<?> getApplicationById(@PathVariable Long applicationId, Principal principal)
    {

        JobPortalUser recruiter = userRepo.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Application application=applicationRepo.findById(applicationId).orElse(null);
        if(application==null)
            return ResponseEntity.badRequest().body("Application Not Exist");
        if(!application.getJob()
                .getCompany()
                .getRecruiter()
                .getId()
                .equals(recruiter.getId())){

            throw new AccessDeniedException("Access Denied");
        }





        JobApplicationResponse response = modelMapper.map(application, JobApplicationResponse.class);
        response.setJobId(application.getJob().getId());
        response.setJobTitle(application.getJob().getTitle());
        response.setCompanyName(application.getJob().getCompany().getCompanyName());


        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


    @PreAuthorize("hasRole('RECRUITER')")
    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<?> getApplicants(@PathVariable Long jobId, Principal principal )
    {
        JobPortalUser recruiter = userRepo.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Job job= jobRepo.findById(jobId).orElseThrow(() -> new UsernameNotFoundException("Job not found"));
        if(!job.getCompany()
                .getRecruiter()
                .getId()
                .equals(recruiter.getId())){

            throw new AccessDeniedException("Access Denied");
        }

        List<Application> applications =
                applicationRepo.findByJob(job);
        if(applications.isEmpty())
            return ResponseEntity.ok(applications);
        List<JobApplicationResponse> dto=new ArrayList<>();

        for(Application application:applications)
        {
            JobApplicationResponse response = modelMapper.map(application, JobApplicationResponse.class);
            response.setJobId(application.getJob().getId());
            response.setJobTitle(application.getJob().getTitle());
            response.setCompanyName(application.getJob().getCompany().getCompanyName());
            dto.add(response);
        }
        return ResponseEntity.status(HttpStatus.OK).body(dto);

    }

    @PreAuthorize("hasRole('RECRUITER')")
    @PatchMapping("/{applicationId}")
    public ResponseEntity<?> updateApplicationStatus(@PathVariable Long applicationId, @RequestParam String status,Principal principal)
    {

        Application application=applicationRepo.findById(applicationId).orElseThrow(()-> new NoSuchElementException("Application not found"));
        JobPortalUser recruiter = userRepo.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!application.getJob()
                .getCompany()
                .getRecruiter()
                .getId()
                .equals(recruiter.getId())) {

            throw new AccessDeniedException("You are not authorized to update this application");
        }

        application.setStatus(ApplicationStatus.valueOf(status.toUpperCase()));
        Application saved=applicationRepo.save(application);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        JobApplicationResponse response = modelMapper.map(saved, JobApplicationResponse.class);
        response.setJobId(application.getJob().getId());
        response.setJobTitle(application.getJob().getTitle());
        response.setCompanyName(application.getJob().getCompany().getCompanyName());

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
