package jobs.com.SmartJobPortal.entity;

import jakarta.persistence.*;

@Entity
@Table(name="Portal_Company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String website;
    private String location;
    @OneToOne
    @JoinColumn(name = "recruiter_id")
    private JobPortalUser recruiter;

    public Company(Long id, String name, String description, String website, String location, JobPortalUser recruiter) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.website = website;
        this.location = location;
        this.recruiter = recruiter;
    }

    public Company() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
