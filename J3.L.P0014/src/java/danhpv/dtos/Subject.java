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
public class Subject {

    private String subjectId;
    private String subjectName;
    private int timeTakeQuiz;
    private int totalQuestion;
    private float pointPerQuestion;

    public Subject() {
    }

    public Subject(String subjectId, String subjectName) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public int getTimeTakeQuiz() {
        return timeTakeQuiz;
    }

    public void setTimeTakeQuiz(int timeTakeQuiz) {
        this.timeTakeQuiz = timeTakeQuiz;
    }

    public int getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(int totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public float getPointPerQuestion() {
        return pointPerQuestion;
    }

    public void setPointPerQuestion(float pointPerQuestion) {
        this.pointPerQuestion = pointPerQuestion;
    }

    public Subject(String subjectId, String subjectName, int timeTakeQuiz, int totalQuestion, float pointPerQuestion) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.timeTakeQuiz = timeTakeQuiz;
        this.totalQuestion = totalQuestion;
        this.pointPerQuestion = pointPerQuestion;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
