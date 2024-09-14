package Fectum.co.in.LPI.Service.Goals;

import Fectum.co.in.LPI.DTO.GoalEvaluationRequest;
import Fectum.co.in.LPI.DTO.MilestoneDTO;
import Fectum.co.in.LPI.Entity.Goals.*;
import Fectum.co.in.LPI.Repository.Goals.GoalEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GoalEvaluationService {

    @Autowired
    private GoalEvaluationRepository goalEvaluationRepository;

    @Autowired
    private GoalService goalService;

    @Autowired
    private MilestoneService milestoneService;

    @Autowired
    private MilestoneEvaluationService milestoneEvaluationService;

    @Autowired
    private EvaluateScore eva;

    public List<GoalEvaluation> getAllGoalEvaluations() {
        return goalEvaluationRepository.findAll();
    }

    public Optional<GoalEvaluation> getGoalEvaluationById(Long evaluationId) {
        return goalEvaluationRepository.findById(evaluationId);
    }

    public ResponseEntity<?> saveGoalEvaluation(GoalEvaluationRequest request) {
        Optional<Goal> optionalGoal = goalService.getGoalById(request.getGoalId());

        if(!optionalGoal.get().getEvaluations().isEmpty()){
            return ResponseEntity.status(400).body("A goal with ID " + request.getGoalId() + " Evaluation is already done.");
        }

        if (optionalGoal.isEmpty()) {
            return ResponseEntity.status(400).body("A goal with ID " + request.getGoalId() + " is not present in our system.");
        } else if (optionalGoal.get().getStatus() != GoalStatus.COMPLETED) {
            return ResponseEntity.status(400).body("A goal with ID " + request.getGoalId() + " can only be created if the associated goal status is COMPLETED.");
        }

        GoalEvaluation goalEvaluation = new GoalEvaluation();
        goalEvaluation.setGoal(optionalGoal.get());
        goalEvaluation.setEvaluationDate(request.getEvaluationDate() != null ? request.getEvaluationDate() : LocalDateTime.now());
        goalEvaluation.setFeedback(request.getFeedback());

        try {
            goalEvaluation.setProgressScore(calculateProgressScore(optionalGoal.get().getType(), optionalGoal.get()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Invalid goal type: " + optionalGoal.get().getType());
        }

        GoalEvaluation savedGoalEvaluation = goalEvaluationRepository.save(goalEvaluation);
        return ResponseEntity.ok(savedGoalEvaluation);
    }

    private double calculateProgressScore(GoalType goalType, Goal goal) {

        switch (goalType) {
            case LONG:
                return LongScore(goal);
            case SHORT:
                return eva.shortTermGoalScore(goal);
            default:
                throw new IllegalArgumentException("Invalid goal type: " + goalType);
        }

    }

    private double LongScore(Goal goal) {
        List<MilestoneDTO> milestoneList=milestoneService.getMilestonesByGoalId(goal.getGoalId());
        if (milestoneList.isEmpty()) {
            return 0.0; // or handle as needed
        }
        // Get all milestone IDs
        List<Long> milestoneIds = milestoneList.stream()
                .map(MilestoneDTO::getMilestoneId)
                .collect(Collectors.toList());
        // Retrieve evaluations for all milestones
        List<MilestoneEvaluation> evaluations = milestoneEvaluationService.getAllMilestoneEvaluations()
                .stream()
                .filter(evaluation -> milestoneIds.contains(evaluation.getMilestone().getMilestoneId()))
                .collect(Collectors.toList());

        // Calculate average progress score
        if (evaluations.isEmpty()) {
            return 0.0; // or handle as needed
        }
        double totalScore = evaluations.stream()
                .mapToDouble(MilestoneEvaluation::getProgressScore)
                .sum();
        return totalScore / milestoneIds.size();
    }


    public void deleteGoalEvaluation(Long evaluationId) {
        goalEvaluationRepository.deleteById(evaluationId);
    }
}
