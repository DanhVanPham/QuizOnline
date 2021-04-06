/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.dtos;


/**
 *
 * @author DELL
 */
public class Answer {
    private String answerId;
    private String answerContent;
    private String questionId;
    private boolean type;

    public Answer() {
    }

    public Answer(String answerId, String answerContent, String questionId, boolean type) {
        this.answerId = answerId;
        this.answerContent = answerContent;
        this.questionId = questionId;
        this.type = type;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

  
    
}
