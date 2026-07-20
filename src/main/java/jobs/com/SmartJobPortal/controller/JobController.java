package jobs.com.SmartJobPortal.controller;

import jobs.com.SmartJobPortal.entity.Company;
import jobs.com.SmartJobPortal.entity.Job;
import jobs.com.SmartJobPortal.entity.JobPortalUser;
import jobs.com.SmartJobPortal.model.JobReqDTO;
import jobs.com.SmartJobPortal.model.JobResDTO;
import jobs.com.SmartJobPortal.repository.JobRepo;
import jobs.com.SmartJobPortal.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/recruiter/job")

public class JobController {
    private final JobRepo jobRepo;
    private final ModelMapper modelMapper;
    private final UserRepo userRepo;

    public JobController(JobRepo jobRepo, ModelMapper modelMapper, UserRepo userRepo) {
        this.jobRepo = jobRepo;
        this.modelMapper = modelMapper;
        this.userRepo = userRepo;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('RECRUITER')")
    public ResponseEntity<?> create(@RequestBody JobReqDTO newJob, Principal principal)
    {
        String username=principal.getName();
        JobPortalUser user=userRepo.findByUsername(username).get();
        Company company=user.getCompany();
        if(company==null)
            return ResponseEntity.badRequest().body("Company Not Registered");
        if(jobRepo.findByTitle(newJob.getTitle()).isPresent() && jobRepo.findByLocation(newJob.getLocation()).isPresent())
            return ResponseEntity.badRequest().body("Job Already Created");
        Job addJob=modelMapper.map(newJob, Job.class);
        addJob.setCompany(company);
        jobRepo.save(addJob);
        return ResponseEntity.status(HttpStatus.CREATED).body("New JOb Created Successfully");

    }

    @GetMapping("/get")
    @PreAuthorize("hasRole('RECRUITER')")
    public ResponseEntity<?> getMyJob( Principal principal)
    {
        String username=principal.getName();
        JobPortalUser user=userRepo.findByUsername(username).get();
        Company company=user.getCompany();
        List<Job> jobs =company.getJobs();
        if(jobs.isEmpty())
            return ResponseEntity.badRequest().body("Job Not Created");
        List<JobResDTO> jobDTOS = new ArrayList<>();

        for (Job job : jobs) {
            JobResDTO dto = modelMapper.map(job, JobResDTO.class);
            jobDTOS.add(dto);
        }

        return ResponseEntity.status(HttpStatus.OK).body(jobDTOS);

    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasRole('RECRUITER')")
    public ResponseEntity<?> updateJob(@RequestBody JobReqDTO newJob, @PathVariable Long id)
    {
        Job existJob=jobRepo.findById(id).orElse(null);

        if(existJob==null)
            return ResponseEntity.badRequest().body("Job Does not Exist");

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(newJob,existJob);
        jobRepo.save(existJob);
        return ResponseEntity.status(HttpStatus.OK).body("Job Updated Successfully");

    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('RECRUITER')")
    public ResponseEntity<?> updateJob( @PathVariable Long id)
    {
        Job existJob=jobRepo.findById(id).orElse(null);

        if(existJob==null)
            return ResponseEntity.badRequest().body("Job Does not Exist");

        jobRepo.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(existJob.getTitle()+" Job Deleted Successfully");

    }
    @GetMapping("/getalljobs")
    public ResponseEntity<?> getAllJobs()
    {
        List<Job> listJobs=jobRepo.findAll();
        if(listJobs.isEmpty())
            return ResponseEntity.badRequest().body("Job Does not Exist");

        List<JobResDTO> jobDTOS = new ArrayList<>();

        for (Job job : listJobs) {
            JobResDTO dto = modelMapper.map(job, JobResDTO.class);
            jobDTOS.add(dto);
        }

        return ResponseEntity.status(HttpStatus.OK).body(jobDTOS);

    }

    @GetMapping("/getjob/{id}")
    public ResponseEntity<?> getJobById(@PathVariable Long id)
    {
        Job existJob=jobRepo.findById(id).orElse(null);

        if(existJob==null)
            return ResponseEntity.badRequest().body("Job Does not Exist");

        JobResDTO dto=modelMapper.map(existJob,JobResDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(dto);

    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String keyword)
    {
        List<Job> jobs=jobRepo.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrSkillsContainingIgnoreCase(keyword,keyword,keyword);
        if(jobs.isEmpty())
            return ResponseEntity.badRequest().body("Job Does not Exist");

        List<JobResDTO> jobDTOS = new ArrayList<>();

        for (Job job : jobs) {
            JobResDTO dto = modelMapper.map(job, JobResDTO.class);
            jobDTOS.add(dto);
        }

        return ResponseEntity.status(HttpStatus.OK).body(jobDTOS);


    }


}
