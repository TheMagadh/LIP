package Fectum.co.in.LPI.Entity.Goals;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "goals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goalId;

    @NotNull
    private Long userId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private GoalType type;

    @Enumerated(EnumType.STRING)
    @NotNull
    private GoalStatus status;

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



    // Relationships
    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Milestone> milestones;

    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<GoalEvaluation> evaluations;


}
