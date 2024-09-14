package Fectum.co.in.LPI.Service.Quiz;

import Fectum.co.in.LPI.Entity.Quiz.Question;
import Fectum.co.in.LPI.Entity.Quiz.Answer;
import Fectum.co.in.LPI.Entity.Quiz.MatchPair;
import Fectum.co.in.LPI.Repository.Quiz.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> findAll() {
        return questionRepository.findAll();
    }


}
