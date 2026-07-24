package jobs.com.SmartJobPortal.repository;

import jobs.com.SmartJobPortal.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepo  extends JpaRepository<Company, Long> {
    Optional<Company> findByCompanyName(String name);
    Optional<Company> findByCompanyNameAndLocation(String name, String location);
}
