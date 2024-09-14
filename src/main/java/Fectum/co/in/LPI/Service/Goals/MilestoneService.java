package Fectum.co.in.LPI.Service.Goals;

import Fectum.co.in.LPI.DTO.MilestoneDTO;
import Fectum.co.in.LPI.Entity.Goals.Milestone;
import Fectum.co.in.LPI.Entity.Goals.Goal;
import Fectum.co.in.LPI.Entity.Goals.GoalStatus;
import Fectum.co.in.LPI.Repository.Goals.MilestoneRepository;
import Fectum.co.in.LPI.Repository.Goals.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MilestoneService {

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private GoalRepository goalRepository;

    /**
     * Converts a Milestone entity to a MilestoneDTO.
     */
    private MilestoneDTO convertToDTO(Milestone milestone) {
        return new MilestoneDTO(
                milestone.getGoal().getGoalId(),
                milestone.getMilestoneId(),
                milestone.getStatus().name(),
                milestone.getDescription(),
                milestone.getComplexity(),
                milestone.getPlannedDuration(),
                milestone.getActualDuration(),
                milestone.getCreationDate(),
                milestone.getStartDate(),
                milestone.getDueDate(),
                milestone.getUpdatedDate(),
                milestone.getPauseDate(),
                milestone.getRestartDate(),
                milestone.getCompletionDate()
        );
    }

    /**
     * Converts a MilestoneDTO to a Milestone entity.
     */
    private Milestone convertToEntity(MilestoneDTO milestoneDTO) {
        Milestone milestone = new Milestone();
        milestone.setDescription(milestoneDTO.getDescription());
        milestone.setComplexity(milestoneDTO.getComplexity());
        milestone.setPlannedDuration(milestoneDTO.getPlannedDuration());
        milestone.setActualDuration(milestoneDTO.getActualDuration());
        milestone.setCreationDate(milestoneDTO.getCreationDate());
        milestone.setStartDate(milestoneDTO.getStartDate());
        milestone.setDueDate(milestoneDTO.getDueDate());
        milestone.setUpdatedDate(milestoneDTO.getUpdatedDate());
        milestone.setPauseDate(milestoneDTO.getPauseDate());
        milestone.setRestartDate(milestoneDTO.getRestartDate());
        milestone.setCompletionDate(milestoneDTO.getCompletionDate());

        // Validate and set status from string
        try {
            GoalStatus status = GoalStatus.valueOf(milestoneDTO.getStatus());
            milestone.setStatus(status);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status value: " + milestoneDTO.getStatus());
        }

        // Associate milestone with the goal using goalId
        Goal goal = goalRepository.findById(milestoneDTO.getGoalId())
                .orElseThrow(() -> new RuntimeException("Goal not found with ID " + milestoneDTO.getGoalId()));
        milestone.setGoal(goal);

        return milestone;
    }

    /**
     * Retrieves all milestones and converts them to DTOs.
     */
    public List<MilestoneDTO> getAllMilestones() {
        return milestoneRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a milestone by its ID.
     */
    public ResponseEntity<MilestoneDTO> getMilestoneById(Long milestoneId) {
        return milestoneRepository.findById(milestoneId)
                .map(milestone -> ResponseEntity.ok(convertToDTO(milestone)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all milestones associated with a specific goal by its ID.
     */
    public List<MilestoneDTO> getMilestonesByGoalId(Long goalId) {
        return milestoneRepository.findByGoal_GoalId(goalId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new milestone.
     */
    public ResponseEntity<?> createMilestone(MilestoneDTO milestoneDTO) {
        Milestone milestone = convertToEntity(milestoneDTO);

        // Validate status on creation
        if (milestone.getStatus() != GoalStatus.IN_PROGRESS) {
            return ResponseEntity.badRequest().body("Milestone cannot be created with status: " + milestone.getStatus());
        }

        // Set start and due dates for new milestones
        milestone.setCreationDate(LocalDateTime.now());
        milestone.setStartDate(LocalDateTime.now());
        milestone.setDueDate(LocalDateTime.now().plusDays(milestone.getPlannedDuration()));

        try {
            Milestone savedMilestone = milestoneRepository.save(milestone);
            return ResponseEntity.ok(convertToDTO(savedMilestone));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred while creating the milestone: " + e.getMessage());
        }
    }

    /**
     * Updates an existing milestone.
     */
    public ResponseEntity<?> updateMilestone(Long milestoneId, MilestoneDTO milestoneDTO) {
        return milestoneRepository.findById(milestoneId).map(existingMilestone -> {
            updateMilestoneDetails(existingMilestone, milestoneDTO);
            milestoneRepository.save(existingMilestone);
            return ResponseEntity.ok(convertToDTO(existingMilestone));
        }).orElseThrow(() -> new RuntimeException("Milestone not found with ID " + milestoneId));
    }

    /**
     * Updates milestone details and handles status transitions.
     */
    private void updateMilestoneDetails(Milestone milestone, MilestoneDTO milestoneDTO) {
        if (milestoneDTO.getStatus() != null) {
            GoalStatus newStatus = GoalStatus.valueOf(milestoneDTO.getStatus());
            handleStatusTransition(milestone, newStatus);
        }

        // Update other details
        if (milestoneDTO.getDescription() != null) {
            milestone.setDescription(milestoneDTO.getDescription());
        }
        if (milestoneDTO.getComplexity() > 0) {
            milestone.setComplexity(milestoneDTO.getComplexity());
        }
        if (milestoneDTO.getPlannedDuration() > 0) {
            milestone.setPlannedDuration(milestoneDTO.getPlannedDuration());
        }

        milestone.setUpdatedDate(LocalDateTime.now());
    }

    /**
     * Handles milestone status transitions.
     */
    private void handleStatusTransition(Milestone milestone, GoalStatus newStatus) {
        switch (newStatus) {
            case IN_PROGRESS:
                handleInProgress(milestone);
                break;
            case ON_HOLD:
                handleOnHold(milestone);
                break;
            case RESTART:
                handleRestart(milestone);
                break;
            case COMPLETED:
                handleCompletion(milestone);
                break;
            default:
                throw new RuntimeException("Invalid status transition.");
        }
        milestone.setStatus(newStatus);
    }

    private void handleInProgress(Milestone milestone) {
        if (milestone.getStartDate() == null) {
            milestone.setStartDate(LocalDateTime.now());
        }
        if (milestone.getDueDate() == null) {
            milestone.setDueDate(LocalDateTime.now().plusDays(milestone.getPlannedDuration()));
        }
    }

    private void handleOnHold(Milestone milestone) {
        if (milestone.getStatus() == GoalStatus.IN_PROGRESS || milestone.getStatus() == GoalStatus.RESTART) {
            long daysInProgress = ChronoUnit.DAYS.between(milestone.getStartDate(), LocalDateTime.now());
            milestone.setActualDuration(milestone.getActualDuration() + (int) daysInProgress);
            milestone.setPauseDate(LocalDateTime.now());
        } else {
            throw new RuntimeException("Milestone can only be placed on hold if it is IN_PROGRESS or RESTART.");
        }
    }

    private void handleRestart(Milestone milestone) {
        if (milestone.getStatus() == GoalStatus.ON_HOLD) {
            milestone.setRestartDate(LocalDateTime.now());
        } else {
            throw new RuntimeException("Milestone can only be restarted if it is ON_HOLD.");
        }
    }

    private void handleCompletion(Milestone milestone) {
        if (milestone.getStatus() == GoalStatus.IN_PROGRESS || milestone.getStatus() == GoalStatus.RESTART) {
            long durationInDays = ChronoUnit.DAYS.between(milestone.getStartDate(), LocalDateTime.now());
            milestone.setActualDuration(milestone.getActualDuration() + (int) durationInDays);
            milestone.setCompletionDate(LocalDateTime.now());
        } else {
            throw new RuntimeException("Milestone can only be completed if it is IN_PROGRESS or RESTART.");
        }
    }

    /**
     * Deletes a milestone by its ID.
     */
    public void deleteMilestone(Long milestoneId) {
        milestoneRepository.deleteById(milestoneId);
    }
}
