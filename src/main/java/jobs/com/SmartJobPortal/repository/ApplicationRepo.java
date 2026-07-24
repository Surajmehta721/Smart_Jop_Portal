package jobs.com.SmartJobPortal.repository;

import jobs.com.SmartJobPortal.entity.Application;
import jobs.com.SmartJobPortal.entity.Job;
import jobs.com.SmartJobPortal.entity.JobPortalUser;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepo extends JpaRepository<Application,Long> {
    Optional<Application> findByJobAndJobSeeker(Job job, JobPortalUser user);

    List<Application> findByJobSeeker(JobPortalUser user);

    List<Application> findByJob(Job job);
}
