/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.daos;

import danhpv.db.MyConnection;
import danhpv.dtos.Subject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class SubjectDAO {

    private Connection connection;
    private PreparedStatement preStm;
    private ResultSet resultSet;

    private void closeConnection() throws Exception {
        if (resultSet != null) {
            resultSet.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    public SubjectDAO() {
    }

    public List<Subject> getAllSubject() throws Exception {
        List<Subject> listSubjects = null;
        String sql = "Select subjectId, subjectName, timeToTakeQuiz, totalOfQuestionsPerQuiz,"
                + " pointPerQuestion from Subject";
        try {
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                resultSet = preStm.executeQuery();
                listSubjects = new ArrayList<>();
                while (resultSet.next()) {
                    String subjectId = resultSet.getString("subjectId");
                    String subjectName = resultSet.getString("subjectName");
                    int timeToTakeQuiz = resultSet.getInt("timeToTakeQuiz");
                    int totalQuestion = resultSet.getInt("totalOfQuestionsPerQuiz");
                    float pointPerQuestion = resultSet.getFloat("pointPerQuestion");
                    Subject subject = new Subject(subjectId, subjectName, timeToTakeQuiz, totalQuestion, pointPerQuestion);
                    listSubjects.add(subject);
                }
            }
        } finally {
            closeConnection();
        }
        return listSubjects;
    }

    public Subject getSubjectBySubjectId(String subjectId) throws Exception {
        Subject subject = null;
        try {
            String sql = "Select subjectName, timeToTakeQuiz, totalOfQuestionsPerQuiz, pointPerQuestion from Subject where subjectId = ?";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, subjectId);
                resultSet = preStm.executeQuery();
                if (resultSet.next()) {
                    String subjectName = resultSet.getString("subjectName");
                    int timeToTakeQuiz = resultSet.getInt("timeToTakeQuiz");
                    int totalQuestion = resultSet.getInt("totalOfQuestionsPerQuiz");
                    float pointPerQuestion = resultSet.getFloat("pointPerQuestion");
                    subject = new Subject();
                    subject.setSubjectId(subjectId);
                    subject.setSubjectName(subjectName);
                    subject.setTimeTakeQuiz(timeToTakeQuiz);
                    subject.setTotalQuestion(totalQuestion);
                    subject.setPointPerQuestion(pointPerQuestion);
                }
            }
        } finally {
            closeConnection();
        }
        return subject;
    }
}
