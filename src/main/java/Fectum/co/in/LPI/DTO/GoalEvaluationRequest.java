package Fectum.co.in.LPI.DTO;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class GoalEvaluationRequest {

    private Long evaluationId; // Optional, can be null for creation

    @NotNull
    private Long goalId; // ID of the goal being evaluated

    private LocalDateTime evaluationDate; // Date of evaluation

    private double progressScore; // Score given in the evaluation

    private String feedback; // Feedback for the evaluation

    // Getters and Setters
    public Long getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(Long evaluationId) {
        this.evaluationId = evaluationId;
    }

    public @NotNull Long getGoalId() {
        return goalId;
    }

    public void setGoalId(@NotNull Long goalId) {
        this.goalId = goalId;
    }

    public LocalDateTime getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(LocalDateTime evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public double getProgressScore() {
        return progressScore;
    }

    public void setProgressScore(double progressScore) {
        this.progressScore = progressScore;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
