package Fectum.co.in.LPI.Service.Goals;

import Fectum.co.in.LPI.Entity.Goals.Goal;
import Fectum.co.in.LPI.Entity.Goals.GoalStatus;
import Fectum.co.in.LPI.Repository.Goals.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    public List<Goal> getAllGoals() {
        return goalRepository.findAll();
    }

    public Optional<Goal> getGoalById(Long goalId) {
        return goalRepository.findById(goalId);
    }

    public List<Goal> getGoalsByUserId(Long userId) {
        return goalRepository.findByUserId(userId);
    }

    public ResponseEntity<?> createGoal(Goal goal) {
        // Prevent creating a goal with a status that should be set during updates
        if (Arrays.asList(GoalStatus.COMPLETED, GoalStatus.ON_HOLD,
                GoalStatus.ABANDONED, GoalStatus.EVALUATED, GoalStatus.OBSTRUCTED).contains(goal.getStatus())) {
            return ResponseEntity.badRequest().body("Goal cannot be created with status: " + goal.getStatus() + ". Please update an existing goal instead.");
        }

        try {
            if (goal.getGoalId() != null) {
                return ResponseEntity.status(409).body("A goal with ID " + goal.getGoalId() + " already exists. Please use a different ID or update the existing goal.");
            } else {
                goal.setCreationDate(LocalDateTime.now());
                if (goal.getStatus() == GoalStatus.IN_PROGRESS) {
                    goal.setStartDate(LocalDateTime.now());
                    goal.setDueDate(LocalDateTime.now().plusDays(goal.getPlannedDuration()));
                }
                Goal savedGoal = goalRepository.save(goal);
                return ResponseEntity.ok(savedGoal);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred while creating the goal. Please try again later or contact support.");
        }
    }

    public ResponseEntity<Goal> updateGoal(Long goalId, Goal goalDetails) {
        return goalRepository.findById(goalId).map(goal -> {
            if (goal.getStatus() == GoalStatus.COMPLETED) {
                throw new RuntimeException("Goal status cannot be changed from Completed!");
            }

            // Update based on status
            if (goalDetails.getStatus() == GoalStatus.ON_HOLD) {
                handleOnHold(goal);
            } else if (goalDetails.getStatus() == GoalStatus.RESTART) {
                handleRestart(goal);
            } else if (goalDetails.getStatus() == GoalStatus.COMPLETED) {
                handleCompletion(goal);
            } else if (goalDetails.getStatus() == GoalStatus.IN_PROGRESS) {
                // Allow only partial updates for IN_PROGRESS

                if (goalDetails.getDescription() != null) {
                    goal.setDescription(goalDetails.getDescription());
                }
                if (goalDetails.getDueDate() == null) {
                    goal.setDueDate(LocalDateTime.now().plusDays(goal.getPlannedDuration()));
                }
                if(goalDetails.getStartDate()==null){
                    goal.setStartDate(LocalDateTime.now());
                }


            }

            // Always update these fields
            goal.setUpdatedDate(LocalDateTime.now()); // Ensure this is updated to the current time
            goal.setStatus(goalDetails.getStatus());  // Update status

            // Save and return the updated goal
            Goal updatedGoal = goalRepository.save(goal);
            return ResponseEntity.ok(updatedGoal); // Return the updated goal with 200 OK status
        }).orElseThrow(() -> new ResourceNotFoundException("Goal not found with id " + goalId));
    }

    private void handleOnHold(Goal goal) {
        if (goal.getStatus() == GoalStatus.IN_PROGRESS) {
            long durationInDays = ChronoUnit.DAYS.between(goal.getStartDate(), LocalDateTime.now());
            goal.setActualDuration(goal.getActualDuration() + (int) durationInDays);
            goal.setPauseDate(LocalDateTime.now());
        } else if (goal.getStatus() == GoalStatus.RESTART) {
            long durationInDays = ChronoUnit.DAYS.between(goal.getRestartDate(), LocalDateTime.now());
            goal.setActualDuration(goal.getActualDuration() + (int) durationInDays);
            goal.setPauseDate(LocalDateTime.now());
        } else {
            throw new RuntimeException("Goal can only be placed on hold if it is IN_PROGRESS or RESTART.");
        }
    }

    private void handleRestart(Goal goal) {
        if (goal.getStatus() == GoalStatus.ON_HOLD) {
            goal.setRestartDate(LocalDateTime.now());
        } else {
            throw new RuntimeException("Goal can only be restarted if it is ON_HOLD.");
        }
    }

    private void handleCompletion(Goal goal) {
        if (goal.getStatus() == GoalStatus.IN_PROGRESS || goal.getStatus() == GoalStatus.RESTART) {
            long durationInDays = ChronoUnit.DAYS.between(goal.getStartDate(), LocalDateTime.now());
            goal.setActualDuration(goal.getActualDuration() + (int) durationInDays);
            goal.setCompletionDate(LocalDateTime.now());
        } else {
            throw new RuntimeException("Goal can only be marked as completed if it is IN_PROGRESS or RESTART.");
        }
    }

    public void deleteGoal(Long goalId) {
        goalRepository.deleteById(goalId);
    }
}
