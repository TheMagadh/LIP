package Fectum.co.in.LPI.Controller.Quiz;

import Fectum.co.in.LPI.DTO.AnswerDTO;
import Fectum.co.in.LPI.DTO.QuestionDTO;
import Fectum.co.in.LPI.Entity.Quiz.Answer;
import Fectum.co.in.LPI.Entity.Quiz.Question;
import Fectum.co.in.LPI.Service.Quiz.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quiz")
public class QuizContoller {

    @Autowired
    private QuestionService questionService;

    @CrossOrigin(origins = "*")
    @Operation(summary = "Get Random Questions", description = "Retrieve 10 random questions from the quiz database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved random questions", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Map.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public ResponseEntity<Map<String, Object>> getRandomQuestions() {
        try {
            List<Question> allQuestions = questionService.findAll();
            int totalQuestions = allQuestions.size();

            // Shuffle the list for randomness
            Collections.shuffle(allQuestions);

            // Select the first 10 random questions
            List<Question> randomQuestions = allQuestions.subList(0, 10);

            // Map to QuestionDTO to exclude correctAnswer
            List<QuestionDTO> randomQuestionDTOs = randomQuestions.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            // Create a map to include both count and questions
            Map<String, Object> response = new HashMap<>();
            response.put("count", 10);
            response.put("questions", randomQuestionDTOs);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Get Random Questions with Answers", description = "Retrieve 10 random questions with answers from the quiz database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved random questions with answers", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Map.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @CrossOrigin(origins = "*")
    @GetMapping("/All")
    public ResponseEntity<Map<String, Object>> getRandomQuestionsWithAnswer() {
        try {
            List<Question> allQuestions = questionService.findAll();
            int totalQuestions = allQuestions.size();

            // Shuffle the list for randomness
            Collections.shuffle(allQuestions);

            // Select the first 10 random questions
            List<Question> randomQuestions = allQuestions.subList(0, 10);

            // Create a map to include both count and questions
            Map<String, Object> response = new HashMap<>();
            response.put("count", 10);
            response.put("questions", randomQuestions);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Get Answer for a Specific Question", description = "Retrieve the correct answer or answers for a specific question based on its type.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the answer(s)", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Map.class))
            }),
            @ApiResponse(responseCode = "404", description = "Question not found"),
            @ApiResponse(responseCode = "400", description = "Invalid question type")
    })
    @CrossOrigin(origins = "*")
    @GetMapping("/answer/{id}")
    public ResponseEntity<Map<String, Object>> getAnswer(@PathVariable Long id) {
        Optional<Question> questionOpt = Optional.ofNullable(questionService.findById(id));
        if (questionOpt.isPresent()) {
            Question question = questionOpt.get();
            String qtype = question.getType().toString();
            Map<String, Object> response = new HashMap<>();

            switch (qtype) {
                case "TRUE_FALSE":
                    response.put("id", id);
                    response.put("answer", question.getCorrectAnswer());
                    break;
                case "MULTIPLE_CHOICE":
                    response.put("id", id);
                    List<Answer> ans=question.getAnswers().stream()
                            .filter(Answer::isCorrect)
                            .collect(Collectors.toList());

                    //response.put("answers", question.getAnswers());
                    response.put("answers", ans);
                    break;
                case "SHORT_ANSWER":
                case "FILL_IN_THE_BLANK":
                    response.put("id", id);
                    response.put("answer", question.getCorrectText());
                    break;
                case "MATCHING":
                    response.put("id", id);
                    response.put("pairs", question.getMatchPairs());
                    break;
                default:
                    return ResponseEntity.status(400).body(null);
            }
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    private QuestionDTO convertToDTO(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setType(String.valueOf(question.getType()));
        dto.setQuestiontext(question.getQuestiontext());
        dto.setAnswers(question.getAnswers().stream().map(this::convertAnswerToDTO).collect(Collectors.toList()));
        // dto.setCorrectText(question.getCorrectText());
        dto.setMatchPairs(question.getMatchPairs());
        return dto;
    }

    private AnswerDTO convertAnswerToDTO(Answer answer) {
        AnswerDTO dto = new AnswerDTO();
        dto.setId(answer.getId());
        dto.setText(answer.getText());
        // Comment out this line if you do not want to include the question in the answer DTO
        //dto.setQuestion(answer.getQuestion());
        return dto;
    }
}
