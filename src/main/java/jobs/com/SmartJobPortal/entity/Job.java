package jobs.com.SmartJobPortal.entity;

import jakarta.persistence.*;
import jobs.com.SmartJobPortal.model.JobStatus;
import jobs.com.SmartJobPortal.model.JobType;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Portal_jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String location;
    private Double salary;
    private String experience;
    @Enumerated(EnumType.STRING)
    private JobType jobType;
    private String skills;
    @Enumerated(EnumType.STRING)
    private JobStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    @OneToMany(
            mappedBy = "job",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Application> applications = new ArrayList<>();


    @PrePersist
    public void onCreate() {
        if (status == null) {
            status = JobStatus.OPEN;
        }
    }

    public Job(Long id, String title, String description, String location, Double salary, String experience, JobType jobType, String skills, JobStatus status, Company company, List<Application> applications) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.salary = salary;
        this.experience = experience;
        this.jobType = jobType;
        this.skills = skills;
        this.status = status;
        this.company = company;
        this.applications = applications;
    }

    public Job() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}
