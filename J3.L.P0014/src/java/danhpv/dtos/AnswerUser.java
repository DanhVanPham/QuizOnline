/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.dtos;

import java.util.Date;

/**
 *
 * @author DELL
 */
public class AnswerUser {
    private String answerUserId;
    private String questionId;
    private String questionContent;
    private String quizId;
    private String answerId;
    private String answerContent;
    private String answerUser;
    private Date createDate;
    private boolean isCorrect;

    public AnswerUser() {
    }

    public AnswerUser(String answerUserId, String questionId, String quizId, String answerUser, Date createDate, boolean isCorrect) {
        this.answerUserId = answerUserId;
        this.questionId = questionId;
        this.quizId = quizId;
        this.answerUser = answerUser;
        this.createDate = createDate;
        this.isCorrect = isCorrect;
    }

    public String getAnswerUserId() {
        return answerUserId;
    }

    public void setAnswerUserId(String answerUserId) {
        this.answerUserId = answerUserId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getAnswerUser() {
        return answerUser;
    }

    public void setAnswerUser(String answerUser) {
        this.answerUser = answerUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }


    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
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
    
    
    
}
