package Fectum.co.in.LPI.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MilestoneEvaluationRequest {

    @NotNull
    private Long milestoneId;

    private LocalDateTime evaluationDate;

    private double progressScore;

    private String feedback;
}
