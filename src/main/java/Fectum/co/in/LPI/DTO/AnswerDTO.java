package Fectum.co.in.LPI.DTO;

import Fectum.co.in.LPI.Entity.Quiz.Question;

public class AnswerDTO {
    private Long id;
    private String text;
    private Question question;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
