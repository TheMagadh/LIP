package Fectum.co.in.LPI.Controller.Goals;

import Fectum.co.in.LPI.DTO.MilestoneEvaluationRequest;
import Fectum.co.in.LPI.Entity.Goals.MilestoneEvaluation;
import Fectum.co.in.LPI.Service.Goals.MilestoneEvaluationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/milestone-evaluations")
@CrossOrigin(origins = "*")
@Tag(name = "Milestone Evaluations", description = "API for managing milestone evaluations")
public class MilestoneEvaluationController {

    @Autowired
    private MilestoneEvaluationService milestoneEvaluationService;

    @Operation(summary = "Get all milestone evaluations", description = "Retrieves all milestone evaluations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all milestone evaluations"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<MilestoneEvaluation> getAllMilestoneEvaluations() {
        return milestoneEvaluationService.getAllMilestoneEvaluations();
    }

    @Operation(summary = "Get milestone evaluation by ID", description = "Retrieves the milestone evaluation by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the milestone evaluation"),
            @ApiResponse(responseCode = "404", description = "Milestone evaluation not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MilestoneEvaluation> getMilestoneEvaluationById(@PathVariable Long id) {
        Optional<MilestoneEvaluation> milestoneEvaluation = milestoneEvaluationService.getMilestoneEvaluationById(id);
        return milestoneEvaluation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new milestone evaluation", description = "Creates a new milestone evaluation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created milestone evaluation"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<?> createMilestoneEvaluation(@Valid @RequestBody MilestoneEvaluationRequest request) {
        return milestoneEvaluationService.saveMilestoneEvaluation(request);
    }

    @Operation(summary = "Delete milestone evaluation", description = "Deletes a milestone evaluation by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted milestone evaluation"),
            @ApiResponse(responseCode = "404", description = "Milestone evaluation not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMilestoneEvaluation(@PathVariable Long id) {
        milestoneEvaluationService.deleteMilestoneEvaluation(id);
        return ResponseEntity.ok().build();
    }
}
