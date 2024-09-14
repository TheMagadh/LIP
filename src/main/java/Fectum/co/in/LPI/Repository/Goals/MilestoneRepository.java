package Fectum.co.in.LPI.Repository.Goals;

import Fectum.co.in.LPI.Entity.Goals.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MilestoneRepository extends JpaRepository<Milestone, Long> {

    // Find milestones by goal ID
    List<Milestone> findByGoal_GoalId(Long goalId);

    // Optional: Add custom queries for specific status or date range if needed
    List<Milestone> findByStatus(String status);
}
