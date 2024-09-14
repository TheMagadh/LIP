package Fectum.co.in.LPI.Entity.Goals;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "milestones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long milestoneId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id")
    @JsonBackReference
    private Goal goal;

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
    @OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MilestoneEvaluation> evaluations;


}
