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
public class QuestionError {
    private String questionContent;
    private String aAnswerError;
    private String bAnswerError;
    private String cAnswerError;
    private String dAnswerError;
    private String answerCorrectError;
    private String subjectIdError;

    public QuestionError() {
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getaAnswerError() {
        return aAnswerError;
    }

    public void setaAnswerError(String aAnswerError) {
        this.aAnswerError = aAnswerError;
    }

    public String getbAnswerError() {
        return bAnswerError;
    }

    public void setbAnswerError(String bAnswerError) {
        this.bAnswerError = bAnswerError;
    }

    public String getcAnswerError() {
        return cAnswerError;
    }

    public void setcAnswerError(String cAnswerError) {
        this.cAnswerError = cAnswerError;
    }

    public String getdAnswerError() {
        return dAnswerError;
    }

    public void setdAnswerError(String dAnswerError) {
        this.dAnswerError = dAnswerError;
    }

    public String getAnswerCorrectError() {
        return answerCorrectError;
    }

    public void setAnswerCorrectError(String answerCorrectError) {
        this.answerCorrectError = answerCorrectError;
    }

    public String getSubjectIdError() {
        return subjectIdError;
    }

    public void setSubjectIdError(String subjectIdError) {
        this.subjectIdError = subjectIdError;
    }
    
}
