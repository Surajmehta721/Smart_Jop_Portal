package jobs.com.SmartJobPortal.repository;

import jobs.com.SmartJobPortal.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepo extends JpaRepository<Job,Long> {

    Optional<Job> findByTitle(String title);
    Optional<Job> findByLocation(String location);
    List<Job> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrSkillsContainingIgnoreCase(String title, String description, String skills);
}
