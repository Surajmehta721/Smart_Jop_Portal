package jobs.com.SmartJobPortal.model;

public class CompanyRequest {
    private String companyName;
    private String description;
    private String website;
    private String location;

    public CompanyRequest(String name, String description, String website, String location) {
        this.companyName = name;
        this.description = description;
        this.website = website;
        this.location = location;
    }

    public CompanyRequest() {
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
}
