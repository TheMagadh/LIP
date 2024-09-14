package Fectum.co.in.LPI.Repository.Quiz;

import Fectum.co.in.LPI.Entity.Quiz.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q LEFT JOIN FETCH q.answers")
    List<Question> findAllWithAnswers();
}
