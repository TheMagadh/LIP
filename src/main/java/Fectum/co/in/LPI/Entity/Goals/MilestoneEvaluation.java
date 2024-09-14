package Fectum.co.in.LPI.Entity.Goals;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "milestone_evaluations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MilestoneEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long evaluationId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id")
    @JsonBackReference
    private Milestone milestone;

    private LocalDateTime evaluationDate;

    private double progressScore;

    private String feedback;
}
