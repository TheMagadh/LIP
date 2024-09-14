package Fectum.co.in.LPI.Service.Quiz;

import Fectum.co.in.LPI.Entity.Quiz.Question;
import Fectum.co.in.LPI.Entity.Quiz.Answer;
import Fectum.co.in.LPI.Entity.Quiz.MatchPair;
import Fectum.co.in.LPI.Repository.Quiz.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Transactional
    public List<Question> createQuestions(List<Question> questions) {
        for (Question question : questions) {
            if (question.getAnswers() != null) {
                for (Answer answer : question.getAnswers()) {
                    answer.setQuestion(question);
                }
            }
            if (question.getMatchPairs() != null) {
                for (MatchPair matchPair : question.getMatchPairs()) {
                    matchPair.setQuestion(question);
                }
            }
        }
        return questionRepository.saveAllAndFlush(questions);
    }

    public List<Question> findAll() {
        Random randomGenerator = new Random();
        return questionRepository.findAll();
    }

    @Transactional
    public void deleteAllQuestions() {
        questionRepository.deleteAll();
    }

    public Question findById(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        return question.orElse(null);
    }
}
