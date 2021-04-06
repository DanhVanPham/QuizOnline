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
public class Quiz {
    private String quizId;
    private String userId;
    private String subjectId;
    private int quizTime;
    private int questionTotal;
    private float maxPoint;
    private float pointUser;
    private Date createDate;
    private int status;

    public Quiz() {
    }

    public Quiz(String quizId, String userId, String subjectId, int quizTime, int questionTotal, float maxPoint, float pointUser, Date createDate, int status) {
        this.quizId = quizId;
        this.userId = userId;
        this.subjectId = subjectId;
        this.quizTime = quizTime;
        this.questionTotal = questionTotal;
        this.maxPoint = maxPoint;
        this.pointUser = pointUser;
        this.createDate = createDate;
        this.status = status;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public int getQuizTime() {
        return quizTime;
    }

    public void setQuizTime(int quizTime) {
        this.quizTime = quizTime;
    }

    public int getQuestionTotal() {
        return questionTotal;
    }

    public void setQuestionTotal(int questionTotal) {
        this.questionTotal = questionTotal;
    }

    public float getMaxPoint() {
        return maxPoint;
    }

    public void setMaxPoint(float maxPoint) {
        this.maxPoint = maxPoint;
    }

    public float getPointUser() {
        return pointUser;
    }

    public void setPointUser(float pointUser) {
        this.pointUser = pointUser;
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

   
    
    
}
