package Fectum.co.in.LPI.Controller.Goals;

import Fectum.co.in.LPI.Entity.Goals.Goal;
import Fectum.co.in.LPI.Entity.Goals.GoalStatus;
import Fectum.co.in.LPI.Service.Goals.GoalService;
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
@RequestMapping("/api/goals")
@CrossOrigin(origins = "*")
@Tag(name = "Goals", description = "API for managing goals")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @Operation(summary = "Get all goals", description = "Retrieves a list of all goals.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all goals",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Goal.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public List<Goal> getAllGoals() {
        return goalService.getAllGoals();
    }

    @Operation(summary = "Get a goal by ID", description = "Retrieves the goal with the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the goal",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Goal.class))),
            @ApiResponse(responseCode = "404", description = "Goal not found",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Goal> getGoalById(@PathVariable Long id) {
        Optional<Goal> goal = goalService.getGoalById(id);
        return goal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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

    @Operation(summary = "Create a new goal", description = "Creates a new goal if no ID is present")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Goal created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Goal.class))),
            @ApiResponse(responseCode = "409", description = "Goal is already present with Id",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Goal cannot be created with a status other than NEW or IN_PROGRESS",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GoalStatus.class)))
    })
    @PostMapping
    public ResponseEntity<?> createGoal(@Valid @RequestBody @Parameter(description = "Goal object to be created", required = true) Goal goal) {
        return goalService.createGoal(goal);
    }

    @Operation(summary = "Update a goal", description = "Updates the goal with the specified ID. The goal's status and other details can be updated based on the provided data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the goal",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Goal.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or goal status transition",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Goal not found with the specified ID",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/{goalId}")
    public ResponseEntity<?> updateGoal(@Valid @PathVariable Long goalId, @RequestBody Goal goal) {
        try {
            Goal savedGoal = goalService.updateGoal(goalId, goal).getBody();
            return ResponseEntity.ok(savedGoal);
        } catch (RuntimeException r) {
            return ResponseEntity.status(500).body(r.getMessage());
        }
    }

    @Operation(summary = "Delete a goal", description = "Deletes the goal with the specified ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Goal deleted successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Goal not found",
                    content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long id) {
        goalService.deleteGoal(id);
        return ResponseEntity.ok().build();
    }
}
