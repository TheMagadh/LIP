package Fectum.co.in.LPI.Controller.Goals;

import Fectum.co.in.LPI.DTO.GoalEvaluationRequest;
import Fectum.co.in.LPI.Entity.Goals.GoalEvaluation;
import Fectum.co.in.LPI.Service.Goals.GoalEvaluationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/goal-evaluations")
@Tag(name = "Goal Evaluations", description = "API for managing goal evaluations")
public class GoalEvaluationController {

    @Autowired
    private GoalEvaluationService goalEvaluationService;

    @Operation(summary = "Get all goal evaluations", description = "Retrieves a list of all goal evaluations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all goal evaluations",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoalEvaluation.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<GoalEvaluation> getAllGoalEvaluations() {
        return goalEvaluationService.getAllGoalEvaluations();
    }

    @Operation(summary = "Get a goal evaluation by ID", description = "Retrieves the goal evaluation with the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the goal evaluation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoalEvaluation.class))),
            @ApiResponse(responseCode = "404", description = "Goal evaluation not found",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<GoalEvaluation> getGoalEvaluationById(@PathVariable Long id) {
        Optional<GoalEvaluation> goalEvaluation = goalEvaluationService.getGoalEvaluationById(id);
        return goalEvaluation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder("Validation failed for fields: ");
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessage.append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage())
                    .append("; ");
        });
        return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Create a new goal evaluation", description = "Creates a new goal evaluation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Goal evaluation created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoalEvaluation.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or goal not found",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<?> createGoalEvaluation(
            @Valid @RequestBody @Parameter(description = "Goal evaluation data", required = true) GoalEvaluationRequest request) {
        return goalEvaluationService.saveGoalEvaluation(request);
    }

    @Operation(summary = "Delete a goal evaluation", description = "Deletes the goal evaluation with the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Goal evaluation deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Goal evaluation not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoalEvaluation(@PathVariable Long id) {
        goalEvaluationService.deleteGoalEvaluation(id);
        return ResponseEntity.ok().build();
    }
}
