package jobs.com.SmartJobPortal.model;

import java.time.LocalDateTime;

public class JobApplicationResponse {
    private Long id;

    private Long jobId;

    private String jobTitle;

    private String companyName;

    private ApplicationStatus status;

    private LocalDateTime appliedAt;

    public JobApplicationResponse(Long id, Long jobId, String jobTitle, String companyName, ApplicationStatus status, LocalDateTime appliedAt) {
        this.id = id;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.status = status;
        this.appliedAt = appliedAt;
    }

    public JobApplicationResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }
}
