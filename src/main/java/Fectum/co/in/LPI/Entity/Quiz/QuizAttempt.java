package Fectum.co.in.LPI.Entity.Quiz;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class QuizAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long startTime;
    private Long endTime;
    private int score;

    @ManyToMany
    @JoinTable(
            name = "quiz_attempt_questions",
            joinColumns = @JoinColumn(name = "quiz_attempt_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> questions;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "quiz_attempt_id")
    private List<QuizResponse> responses;
}
