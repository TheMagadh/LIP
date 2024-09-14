package Fectum.co.in.LPI.Repository.Quiz;

import Fectum.co.in.LPI.Entity.Quiz.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
    List<QuizAttempt> findAllByUserId(Long userId);
}
