package Fectum.co.in.LPI.DTO;

import Fectum.co.in.LPI.Entity.Quiz.MatchPair;

import java.util.List;

public class QuestionDTO {
    private Long id;
    private String type;
    private String questiontext;
    private List<AnswerDTO> answers;
    private String correctText;
    private List<MatchPair> matchPairs;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getQuestiontext() {
        return questiontext;
    }

    public void setQuestiontext(String questiontext) {
        this.questiontext = questiontext;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }

//    public String getCorrectText() {
//        return correctText;
//    }
//
//    public void setCorrectText(String correctText) {
//        this.correctText = correctText;
//    }

    public List<MatchPair> getMatchPairs() {
        return matchPairs;
    }

    public void setMatchPairs(List<MatchPair> matchPairs) {
        this.matchPairs = matchPairs;
    }
}
