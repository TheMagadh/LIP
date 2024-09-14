package Fectum.co.in.LPI.Repository.Goals;

import Fectum.co.in.LPI.Entity.Goals.GoalEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalEvaluationRepository extends JpaRepository<GoalEvaluation, Long> {
}
