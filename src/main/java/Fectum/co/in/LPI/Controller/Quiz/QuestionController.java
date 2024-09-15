package Fectum.co.in.LPI.Controller.Quiz;

import Fectum.co.in.LPI.Entity.Quiz.Question;
import Fectum.co.in.LPI.Service.Quiz.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Operation(summary = "Get a question by ID", description = "Retrieve a specific question using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the question"),
            @ApiResponse(responseCode = "404", description = "Question not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable Long id) {
        try {
            Question question = questionService.findById(id);
            if (question != null) {
                return ResponseEntity.ok(question);
            } else {
                return ResponseEntity.status(404).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Create multiple questions", description = "Bulk create multiple questions.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created questions"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/bulk")
    public ResponseEntity<List<Question>> createQuestions(@RequestBody List<Question> questions) {
        try {
            List<Question> savedQuestions = questionService.createQuestions(questions);
            return ResponseEntity.ok(savedQuestions);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Get all questions", description = "Retrieve a list of all questions.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all questions"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<Question>> findAll() {
        List<Question> allQuestions = questionService.findAll();
        return ResponseEntity.ok(allQuestions);
    }

    @Operation(summary = "Delete all questions", description = "Delete all questions from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted all questions"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllQuestions() {
        try {
            questionService.deleteAllQuestions();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
