package jobs.com.SmartJobPortal.model;

public class ApplyJobRequest {

    private String resumeUrl;

    public ApplyJobRequest(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public ApplyJobRequest() {
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }
}
