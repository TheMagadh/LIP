package Fectum.co.in.LPI.Service.Goals;

import Fectum.co.in.LPI.DTO.MilestoneDTO;
import Fectum.co.in.LPI.Entity.Goals.Goal;
import Fectum.co.in.LPI.Entity.Goals.Milestone;
import Fectum.co.in.LPI.Entity.Goals.MilestoneEvaluation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EvaluateScore {

    public double shortTermGoalScore(Goal goal) {
        int plannedDuration = goal.getPlannedDuration();
        int actualDuration = goal.getActualDuration();
        int complexity = goal.getComplexity();
        double factor = 0.2;
        int maxComplexity = 5;
        int bonusBoundary = 50;
        int penaltyBoundary = -50;

        // Calculate the percentage deviation score (pds)
        double pds = ((double) (plannedDuration - actualDuration) / plannedDuration) * 100;

        // Core score calculation based on the provided formula
        int coreScore = Math.min(Math.max((int) (((double) (plannedDuration - actualDuration) / plannedDuration) * 100), penaltyBoundary), 100);

        // Apply bonus if applicable
        double bonus = pds > bonusBoundary ? (pds - bonusBoundary) * factor : 0;

        // Apply penalty if applicable
        double penalty = pds < penaltyBoundary ? (Math.abs(pds) - penaltyBoundary) * factor : 0;

        // Final score calculation with complexity adjustment
        int finalScore = (int) ((coreScore + bonus - penalty) * ((double) complexity / maxComplexity));

        return finalScore;
    }

    public double longTermGoalScore(Goal goal) {
//        // Implement your logic for long-term goal score here
//        // Placeholder implementation
//        // Retrieve milestones associated with the given goal
//       // List<Milestone> milestoneList = milestoneService.getMilestonesByGoalId(goal.getGoalId());
//
//        // Check if there are no milestones
//        if (milestoneList.isEmpty()) {
//            return 0.0; // or handle as needed
//        }
//
//        // Get all milestone IDs
//        List<Long> milestoneIds = milestoneList.stream()
//                .map(MilestoneDTO::getMilestoneId)
//                .collect(Collectors.toList());
//
//        // Retrieve evaluations for all milestones
//        List<MilestoneEvaluation> evaluations = milestoneEvaluationService.getAllMilestoneEvaluations()
//                .stream()
//                .filter(evaluation -> milestoneIds.contains(evaluation.getMilestone().getMilestoneId()))
//                .collect(Collectors.toList());
//
//        // Calculate average progress score
//        if (evaluations.isEmpty()) {
//            return 0.0; // or handle as needed
//        }
//
//        double totalScore = evaluations.stream()
//                .mapToDouble(MilestoneEvaluation::getProgressScore)
//                .sum();
//        return totalScore / evaluations.size();
        return 0;
    }

    public double milestoneScore(Milestone milestone) {
        int plannedDuration = milestone.getPlannedDuration();
        int actualDuration = milestone.getActualDuration();
        int complexity = milestone.getComplexity();
        double factor = 0.2;
        int maxComplexity = 5;
        int bonusBoundary = 50;
        int penaltyBoundary = -50;

        // Calculate the percentage deviation score (pds)
        double pds = ((double) (plannedDuration - actualDuration) / plannedDuration) * 100;

        // Core score calculation based on the provided formula
        int coreScore = Math.min(Math.max((int) (((double) (plannedDuration - actualDuration) / plannedDuration) * 100), penaltyBoundary), 100);

        // Apply bonus if applicable
        double bonus = pds > bonusBoundary ? (pds - bonusBoundary) * factor : 0;

        // Apply penalty if applicable
        double penalty = pds < penaltyBoundary ? (Math.abs(pds) - penaltyBoundary) * factor : 0;

        // Final score calculation with complexity adjustment
        int finalScore = (int) ((coreScore + bonus - penalty) * ((double) complexity / maxComplexity));

        return finalScore;
    }
}
