/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.daos;

import danhpv.db.MyConnection;
import danhpv.dtos.Question;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author DELL
 */
public class QuestionDAO {

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

    public QuestionDAO() {
    }

    public String getLastQuestionAdded() throws Exception {
        String questionId = null;
        try {
            String sql = "select questionId from Question where createDate = (select max(createDate) from Question)";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                resultSet = preStm.executeQuery();
                if (resultSet.next()) {
                    questionId = resultSet.getString("questionId");
                }
            }
        } finally {
            closeConnection();
        }
        return questionId;
    }

    public long getCountAllQuestionBySearchWithAdmin(String nameSearch, String subjectId, String status) throws Exception {
        long count = 0;
        String sql = "select count(questionId) as 'COUNT' from Question where (? is null or status = ?) and "
                + "(? is null or subjectId = ?) and question_content like ?";
        try {
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, status);
                preStm.setString(2, status);
                preStm.setString(3, subjectId);
                preStm.setString(4, subjectId);
                preStm.setString(5, "%" + nameSearch + "%");
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

    public List<Question> getListPagingQuestionBySearchWithAdmin(String nameSearch, String subjectId, String status, int indexStart, int pageSize) throws Exception {
        List<Question> listQuestion = null;
        String sql = "select questionId, question_content, subjectId, createDate, status "
                + "from Question where (? is null or status = ?) and (? is null or subjectId = ?) "
                + "and question_content like ?\n"
                + "group by question_content, questionId, createDate, status, subjectId order by "
                + "question.question_content ASC offset ? rows fetch next ? rows only";
        try {
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, status);
                preStm.setString(2, status);
                preStm.setString(3, subjectId);
                preStm.setString(4, subjectId);
                preStm.setString(5, "%" + nameSearch + "%");
                preStm.setInt(6, indexStart);
                preStm.setInt(7, pageSize);
                resultSet = preStm.executeQuery();
                listQuestion = new ArrayList<>();
                while (resultSet.next()) {
                    String questionId = resultSet.getString("questionId");
                    String questionContent = resultSet.getString("question_content");
                    String subjId = resultSet.getString("subjectId");
                    Date createDate = new java.util.Date(resultSet.getTimestamp("createDate").getTime());
                    int statusQuiz = resultSet.getInt("status");
                    Question question = new Question(questionId, questionContent, createDate, subjId, statusQuiz);
                    listQuestion.add(question);
                }
            }
        } finally {
            closeConnection();
        }
        return listQuestion;
    }

    public Question getQuestionByQuestionId(String questionId) throws Exception {
        Question question = null;
        String sql = "select subjectId, question_content, status "
                + "from Question where questionId = ?";
        try {
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, questionId);
                resultSet = preStm.executeQuery();
                if (resultSet.next()) {
                    String subjectId = resultSet.getString("subjectId");
                    String questionContent = resultSet.getString("question_content");
                    int status = resultSet.getInt("status");
                    question = new Question();
                    question.setQuestionId(questionId);
                    question.setQuestionContent(questionContent);
                    question.setStatus(status);
                    question.setSubjectId(subjectId);
                }
            }
        } finally {
            closeConnection();
        }
        return question;
    }

    public boolean removeQuestionByQuestion(Question question) throws Exception {
        boolean checkSuccess = false;
        String sql = "UPDATE Question "
                + "SET status = ? "
                + "WHERE questionId = ?";
        try {
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setInt(1, question.getStatus());
                preStm.setString(2, question.getQuestionId());
                checkSuccess = preStm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return checkSuccess;
    }

    public boolean createNewQuestion(Question question) throws Exception {
        boolean checkSuccess = false;
        String sql = "insert into Question(questionId, subjectId, question_content, createDate, status)\n"
                + " values(?, ?, ?, ?, ?)";
        try {
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, question.getQuestionId());
                preStm.setString(2, question.getSubjectId());
                preStm.setString(3, question.getQuestionContent());
                preStm.setTimestamp(4, new Timestamp(question.getCreateDate().getTime()));
                preStm.setInt(5, question.getStatus());
                checkSuccess = preStm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return checkSuccess;
    }

    public boolean updateQuestionByQuestionExisted(Question question) throws Exception {
        boolean checkSuccess = false;
        try {
            String sql = "Update Question "
                    + "SET subjectId = ?,  question_content = ? "
                    + "Where questionId = ?";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                connection.setAutoCommit(false);
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, question.getSubjectId());
                preStm.setString(2, question.getQuestionContent());
                preStm.setString(3, question.getQuestionId());
                checkSuccess = preStm.executeUpdate() > 0;
                connection.commit();
            }
        } finally {
            closeConnection();
        }
        return checkSuccess;
    }

    public List<Question> getListQuestionByRandomN(int numberRandom, String subjectId) throws Exception {
        List<Question> listQuestion = null;
        String sql = "select top (?) questionId from Question question\n"
                + "                where subjectId = ? and  status = ? order by newid()";
        try {
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setInt(1, numberRandom);
                preStm.setString(2, subjectId);
                preStm.setInt(3, 1);
                resultSet = preStm.executeQuery();
                listQuestion = new ArrayList<>();
                while (resultSet.next()) {
                    String questionId = resultSet.getString("questionId");
                    Question question = new Question();
                    question.setQuestionId(questionId);
                    listQuestion.add(question);
                }
            }
        } finally {
            closeConnection();
        }
        return listQuestion;
    }
}
