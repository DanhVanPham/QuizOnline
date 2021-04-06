/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.daos;

import danhpv.db.MyConnection;
import danhpv.dtos.Answer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class AnswerDAO {

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

    public AnswerDAO() {
    }

    public boolean createNewAnswer(Answer answer) throws Exception {
        boolean checkSuccess = false;
        try {
            String sql = "Insert into Answer(answerId, answerContent, questionId, type) "
                    + "values(?, ?, ?, ?)";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, answer.getAnswerId());
                preStm.setString(2, answer.getAnswerContent());
                preStm.setString(3, answer.getQuestionId());
                preStm.setBoolean(4, answer.isType());
                checkSuccess = preStm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return checkSuccess;
    }

    public long getCountAllQuestionByQuizId(String quizId) throws Exception {
        long count = 0;
        String sql = "select count(answerId) as 'COUNT' from Answer where quizId = ?";
        try {
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, quizId);
                resultSet = preStm.executeQuery();
                if (resultSet.next()) {
                    count = resultSet.getLong("COUNT");
                }
            }
        } finally {
            closeConnection();
        }
        return count;
    }

    public boolean getAnswerIsCorrectByAnswerId(String answerId) throws Exception {
        boolean isCorrect = false;
        try {
            String sql = "select type from Answer where answerId = ?";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, answerId);
                resultSet = preStm.executeQuery();
                if (resultSet.next()) {
                    isCorrect = resultSet.getBoolean("type");
                }
            }
        } finally {
            closeConnection();
        }
        return isCorrect;
    }

    public List<Answer> getListAnswerByQuestionId(String questionId) throws Exception {
        List<Answer> listAnswer = null;
        String sql = "select answerId, answerContent,  type from Answer\n"
                + " where questionId = ? ";
        try {
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, questionId);
                resultSet = preStm.executeQuery();
                listAnswer = new ArrayList<>();
                while (resultSet.next()) {
                    String answerId = resultSet.getString("answerId");
                    String answerContent = resultSet.getString("answerContent");
                    boolean isCorrect = resultSet.getBoolean("type");
                    Answer answer = new Answer();
                    answer.setAnswerId(answerId);
                    answer.setAnswerContent(answerContent);
                    answer.setType(isCorrect);
                    listAnswer.add(answer);
                }
            }
        } finally {
            closeConnection();
        }
        return listAnswer;
    }

    public boolean updateAnswerByAnswerExisted(Answer answer) throws Exception {
        boolean checkSuccess = false;
        try {
            String sql = "Update Answer "
                    + "SET answerContent = ?,  type = ? "
                    + "Where answerId = ?";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, answer.getAnswerContent());
                preStm.setBoolean(2, answer.isType());
                preStm.setString(3, answer.getAnswerId());
                checkSuccess = preStm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return checkSuccess;
    }

}
