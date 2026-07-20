package jobs.com.SmartJobPortal.controller;

import jobs.com.SmartJobPortal.entity.Company;
import jobs.com.SmartJobPortal.entity.JobPortalUser;
import jobs.com.SmartJobPortal.model.CompanyRequest;
import jobs.com.SmartJobPortal.repository.CompanyRepo;
import jobs.com.SmartJobPortal.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("api/recruiter/company")
public class CompanyController {

    private final CompanyRepo companyRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    public CompanyController(CompanyRepo companyRepo, UserRepo userRepo, ModelMapper modelMapper) {
        this.companyRepo = companyRepo;
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('RECRUITER')")
    public ResponseEntity<?> create(@RequestBody CompanyRequest company , Principal principal)
    {
        Optional<Company>  db_company=companyRepo.findByNameAndLocation(company.getName(), company.getLocation());
        if(db_company.isPresent())
            return ResponseEntity.badRequest().body(company.getName()+" Already Registered for "+company.getLocation()+" Location");

        JobPortalUser recruiter =userRepo.findByUsername(principal.getName()).orElse(null);
        Company company1=new Company();
        company1.setName(company.getName());
        company1.setDescription(company.getDescription());
        company1.setLocation(company.getLocation());
        company1.setWebsite(company.getWebsite());
        company1.setRecruiter(recruiter);
        companyRepo.save(company1);
        return ResponseEntity.status(HttpStatus.CREATED).body(company.getName()+" Registered Successfully for "+company.getLocation()+" Location");


    }
    @GetMapping("/get")
    @PreAuthorize("hasRole('RECRUITER')")
    public ResponseEntity<?> get_company(Principal principal)
    {
        String username=principal.getName();
        JobPortalUser user=userRepo.findByUsername(username).get();
        Company company=user.getCompany();
        if(company==null)
            return ResponseEntity.notFound().build();

        CompanyRequest get_Company=modelMapper.map(company, CompanyRequest.class);
        return ResponseEntity.status(HttpStatus.OK).body(get_Company);


    }

    @PatchMapping("/update")
    @PreAuthorize("hasRole('RECRUITER')")
    public ResponseEntity<?> update(@RequestBody CompanyRequest company_dto ,Principal principal)
    {

        String username=principal.getName();
        JobPortalUser user=userRepo.findByUsername(username).get();
        Company exist_company=user.getCompany();
        if(exist_company==null)
            return ResponseEntity.badRequest().body("Company Not Registered");
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(company_dto,  exist_company);

        companyRepo.save(exist_company);
        return ResponseEntity.status(HttpStatus.OK).body(exist_company.getName()+" Updated Successfully");


    }
}
