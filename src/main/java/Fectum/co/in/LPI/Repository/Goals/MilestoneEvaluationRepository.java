package Fectum.co.in.LPI.Repository.Goals;

import Fectum.co.in.LPI.Entity.Goals.MilestoneEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MilestoneEvaluationRepository extends JpaRepository<MilestoneEvaluation, Long> {
}
