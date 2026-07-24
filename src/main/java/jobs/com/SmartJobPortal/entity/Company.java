package jobs.com.SmartJobPortal.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Portal_Company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String companyName;
    private String description;
    private String website;
    private String location;
    @OneToOne
    @JoinColumn(name = "recruiter_id")
    private JobPortalUser recruiter;

    @OneToMany(
            mappedBy = "company",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Job> jobs = new ArrayList<>();

    public Company(Long id, String name, String description, String website, String location, JobPortalUser recruiter, List<Job> jobs) {
        this.id = id;
        this.companyName = name;
        this.description = description;
        this.website = website;
        this.location = location;
        this.recruiter = recruiter;
        this.jobs = jobs;
    }

    public Company() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String name) {
        this.companyName = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public JobPortalUser getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(JobPortalUser recruiter) {
        this.recruiter = recruiter;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
