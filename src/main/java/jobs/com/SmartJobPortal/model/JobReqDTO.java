package jobs.com.SmartJobPortal.model;

public class JobReqDTO {

    private String title;
    private String description;
    private String location;
    private Double salary;
    private String experience;
    private JobType jobType;
    private String skills;
    private JobStatus status;

    public JobReqDTO(String title, String description, String location, Double salary, String experience, JobType jobType, String skills, JobStatus status) {

        this.title = title;
        this.description = description;
        this.location = location;
        this.salary = salary;
        this.experience = experience;
        this.jobType = jobType;
        this.skills = skills;
        this.status = status;
    }

    public JobReqDTO() {
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
}
