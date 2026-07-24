package jobs.com.SmartJobPortal.entity;

import jakarta.persistence.*;
import jobs.com.SmartJobPortal.model.ApplicationStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(length = 500)
    private String resumeUrl;

    private LocalDateTime appliedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private JobPortalUser jobSeeker;

    @PrePersist
    public void onCreate() {
        appliedAt = LocalDateTime.now();
        if (status == null) {
            status = ApplicationStatus.APPLIED;
        }
    }

    public Application(Long id, ApplicationStatus status, String resumeUrl, LocalDateTime appliedAt, Job job, JobPortalUser jobSeeker) {
        this.id = id;
        this.status = status;
        this.resumeUrl = resumeUrl;
        this.appliedAt = appliedAt;
        this.job = job;
        this.jobSeeker = jobSeeker;
    }

    public Application() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public JobPortalUser getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobPortalUser jobSeeker) {
        this.jobSeeker = jobSeeker;
    }
}
