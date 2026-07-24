package jobs.com.SmartJobPortal.entity;

import jakarta.persistence.*;
import jobs.com.SmartJobPortal.model.Role;

import java.util.ArrayList;
import java.util.List;

@Entity
public class JobPortalUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(mappedBy = "recruiter", cascade = CascadeType.ALL)
    private Company company;


    @OneToMany(mappedBy = "jobSeeker")
    private List<Application> applications = new ArrayList<>();

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public JobPortalUser(Long id, String username, String email, String password, Role role, Company company, List<Application> applications) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.company = company;
        this.applications = applications;
    }

    public JobPortalUser() {
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
