package Fectum.co.in.LPI.Controller.Goals;

import Fectum.co.in.LPI.DTO.MilestoneDTO;
import Fectum.co.in.LPI.Service.Goals.MilestoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/milestones")
public class MilestoneController {

    @Autowired
    private MilestoneService milestoneService;

    /**
     * Retrieves all milestones.
     */
    @Operation(summary = "Get all milestones", description = "Retrieves a list of all milestones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of milestones"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<MilestoneDTO>> getAllMilestones() {
        List<MilestoneDTO> milestones = milestoneService.getAllMilestones();
        return ResponseEntity.ok(milestones);
    }

    /**
     * Retrieves a milestone by its ID.
     */
    @Operation(summary = "Get milestone by ID", description = "Retrieves a milestone by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the milestone"),
            @ApiResponse(responseCode = "404", description = "Milestone not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MilestoneDTO> getMilestoneById(@PathVariable Long id) {
        return milestoneService.getMilestoneById(id);
    }

    /**
     * Retrieves all milestones associated with a specific goal by its ID.
     */
    @Operation(summary = "Get milestones by goal ID", description = "Retrieves all milestones associated with a specific goal by goal ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of milestones for the goal"),
            @ApiResponse(responseCode = "404", description = "Goal not found")
    })
    @GetMapping("/goal/{goalId}")
    public ResponseEntity<List<MilestoneDTO>> getMilestonesByGoalId(@PathVariable Long goalId) {
        List<MilestoneDTO> milestones = milestoneService.getMilestonesByGoalId(goalId);
        return ResponseEntity.ok(milestones);
    }

    /**
     * Creates a new milestone.
     */
    @Operation(summary = "Create milestone", description = "Creates a new milestone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the milestone"),
            @ApiResponse(responseCode = "400", description = "Invalid milestone data provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<?> createMilestone(@RequestBody MilestoneDTO milestoneDTO) {
        return milestoneService.createMilestone(milestoneDTO);
    }

    /**
     * Updates an existing milestone.
     */
    @Operation(summary = "Update milestone", description = "Updates an existing milestone by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the milestone"),
            @ApiResponse(responseCode = "404", description = "Milestone not found"),
            @ApiResponse(responseCode = "400", description = "Invalid milestone data provided")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMilestone(@PathVariable Long id, @RequestBody MilestoneDTO milestoneDTO) {
        return milestoneService.updateMilestone(id, milestoneDTO);
    }

    /**
     * Deletes a milestone by its ID.
     */
    @Operation(summary = "Delete milestone", description = "Deletes a milestone by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the milestone"),
            @ApiResponse(responseCode = "404", description = "Milestone not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMilestone(@PathVariable Long id) {
        milestoneService.deleteMilestone(id);
        return ResponseEntity.ok().build();
    }
}
