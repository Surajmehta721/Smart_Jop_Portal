package jobs.com.SmartJobPortal.repository;

import jobs.com.SmartJobPortal.entity.Company;
import jobs.com.SmartJobPortal.entity.JobPortalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepo  extends JpaRepository<Company, Long> {
    Optional<Company> findByName(String name);
    Optional<Company> findByNameAndLocation(String name, String location);
}
