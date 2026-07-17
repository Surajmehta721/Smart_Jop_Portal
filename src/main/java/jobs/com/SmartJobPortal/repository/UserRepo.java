package jobs.com.SmartJobPortal.repository;

import jobs.com.SmartJobPortal.entity.JobPortalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<JobPortalUser,Long> {
    Optional<JobPortalUser> findByUsername(String username);
    Optional<JobPortalUser> findByEmail(String email);
    Optional<JobPortalUser> findByEmailOrUsername(String email, String username);


}
