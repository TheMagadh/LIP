package Fectum.co.in.LPI.DTO;

import Fectum.co.in.LPI.Entity.Goals.GoalStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class MilestoneDTO {

    private Long milestoneId;

    @NotNull
    private Long goalId;

    @NotNull
    private String status; // This will be a string representation of the GoalStatus enum

    @NotBlank
    private String description;

    @Min(1)
    @Max(5)
    private int complexity;

    @Min(1)
    private int plannedDuration; // in days

    private int actualDuration; // in days

    private LocalDateTime creationDate;

    private LocalDateTime startDate;

    private LocalDateTime dueDate;

    private LocalDateTime updatedDate;

    private LocalDateTime pauseDate;

    private LocalDateTime restartDate;

    private LocalDateTime completionDate;

    // Default constructor
    public MilestoneDTO() {}

    // Parameterized constructor
    public MilestoneDTO(Long goalId,Long milestoneId, String status, String description, int complexity, int plannedDuration,
                        int actualDuration, LocalDateTime creationDate, LocalDateTime startDate, LocalDateTime dueDate,
                        LocalDateTime updatedDate, LocalDateTime pauseDate, LocalDateTime restartDate, LocalDateTime completionDate) {
        this.goalId = goalId;
        this.milestoneId=milestoneId;
        this.status = status;
        this.description = description;
        this.complexity = complexity;
        this.plannedDuration = plannedDuration;
        this.actualDuration = actualDuration;
        this.creationDate = creationDate;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.updatedDate = updatedDate;
        this.pauseDate = pauseDate;
        this.restartDate = restartDate;
        this.completionDate = completionDate;
    }

    public Long getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(Long milestoneId) {
        this.milestoneId = milestoneId;
    }

    // Getters and Setters
    public @NotNull Long getGoalId() {
        return goalId;
    }

    public void setGoalId(@NotNull Long goalId) {
        this.goalId = goalId;
    }

    public @NotNull String getStatus() {
        return status;
    }

    public void setStatus(@NotNull String status) {
        this.status = status;
    }

    public @NotBlank String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank String description) {
        this.description = description;
    }

    @Min(1)
    @Max(5)
    public int getComplexity() {
        return complexity;
    }

    public void setComplexity(@Min(1) @Max(5) int complexity) {
        this.complexity = complexity;
    }

    @Min(1)
    public int getPlannedDuration() {
        return plannedDuration;
    }

    public void setPlannedDuration(@Min(1) int plannedDuration) {
        this.plannedDuration = plannedDuration;
    }

    public int getActualDuration() {
        return actualDuration;
    }

    public void setActualDuration(int actualDuration) {
        this.actualDuration = actualDuration;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public LocalDateTime getPauseDate() {
        return pauseDate;
    }

    public void setPauseDate(LocalDateTime pauseDate) {
        this.pauseDate = pauseDate;
    }

    public LocalDateTime getRestartDate() {
        return restartDate;
    }

    public void setRestartDate(LocalDateTime restartDate) {
        this.restartDate = restartDate;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }
}
