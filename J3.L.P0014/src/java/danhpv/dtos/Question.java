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
public class Question {
    private String questionId;
    private String questionContent;
    private String answerContent;
    private boolean isCorrect;
    private Date createDate;
    private int status;
    private String subjectId;

    public Question() {
    }

    public Question(String questionId, String questionContent, Date createDate, String subjectId, int status) {
        this.questionId = questionId;
        this.questionContent = questionContent;
        this.createDate = createDate;
        this.status = status;
        this.subjectId = subjectId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public boolean isIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

  

    
    
}