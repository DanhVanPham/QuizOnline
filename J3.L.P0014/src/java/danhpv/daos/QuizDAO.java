/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.daos;

import danhpv.db.MyConnection;
import danhpv.dtos.Quiz;
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
public class QuizDAO {

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

    public QuizDAO() {
    }

    public Quiz getQuizByQuizId(String quizId) throws Exception {
        Quiz quiz = null;
        try {
            String sql = "Select quizId, subjectId, questionTotal, maxPoint from Quiz where quizId = ?";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, quizId);
                resultSet = preStm.executeQuery();
                if (resultSet.next()) {
                    String getQuizId = resultSet.getString("quizId");
                    String subjectId = resultSet.getString("subjectId");
                    int questionTotal = resultSet.getInt("questionTotal");
                    float maxPoint = resultSet.getFloat("maxPoint");
                    quiz = new Quiz();
                    quiz.setQuizId(getQuizId);
                    quiz.setSubjectId(subjectId);
                    quiz.setMaxPoint(maxPoint);
                    quiz.setQuestionTotal(questionTotal);
                }
            }
        } finally {
            closeConnection();
        }
        return quiz;
    }

    public long getCountAllQuizByUserIdAndSubjectName(String userId, String subjectName) throws Exception {
        long countAll = 0;
        try {
            String sql = "Select count(quizId) as 'COUNT' from Quiz quiz join Subject  subject "
                    + " on quiz.subjectId = subject.subjectId where userId = ? and subjectName like ? and "
                    + "status = ?";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, userId);
                preStm.setString(2, "%" + subjectName + "%");
                preStm.setInt(3, 1);
                resultSet = preStm.executeQuery();
                if (resultSet.next()) {
                    countAll = resultSet.getLong("COUNT");
                }
            }
        } finally {
            closeConnection();
        }
        return countAll;
    }

    public List<Quiz> getListPagningQuizByUserIdAndSubjectName(String userId, String subjectName, int indexPage, int pageSize) throws Exception {
        List<Quiz> listQuiz = null;
        try {
            String sql = "Select quizId, quiz.subjectId, quizTime, questionTotal, maxPoint, "
                    + "pointUser, createDate from Quiz quiz join Subject subject on "
                    + "quiz.subjectId = subject.subjectId \n"
                    + "where userId = ? and subjectName like ?  and status = ?\n"
                    + "order by createDate desc offset ? rows fetch next ? rows only";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, userId);
                preStm.setString(2, "%" + subjectName + "%");
                preStm.setInt(3, 1);
                preStm.setInt(4, indexPage);
                preStm.setInt(5, pageSize);
                resultSet = preStm.executeQuery();
                listQuiz = new ArrayList<>();
                while (resultSet.next()) {
                    String getQuizId = resultSet.getString("quizId");
                    String getSubjectId = resultSet.getString("subjectId");
                    int quizTime = resultSet.getInt("quizTime");
                    int questionTotal = resultSet.getInt("questionTotal");
                    float maxPoint = resultSet.getFloat("maxPoint");
                    float pointUser = resultSet.getFloat("pointUser");
                    Date date = new Date(resultSet.getTimestamp("createDate").getTime());
                    Quiz quiz = new Quiz();
                    quiz.setQuizId(getQuizId);
                    quiz.setSubjectId(getSubjectId);
                    quiz.setMaxPoint(maxPoint);
                    quiz.setQuestionTotal(questionTotal);
                    quiz.setQuizTime(quizTime);
                    quiz.setMaxPoint(maxPoint);
                    quiz.setPointUser(pointUser);
                    quiz.setCreateDate(date);
                    listQuiz.add(quiz);
                }
            }
        } finally {
            closeConnection();
        }
        return listQuiz;
    }

    public long getCountAllQuizBySubjectNameWithAdmin(String subjectName) throws Exception {
        long countAll = 0;
        try {
            String sql = "Select count(quizId) as 'COUNT' from Quiz quiz join Subject  subject "
                    + " on quiz.subjectId = subject.subjectId where subjectName like ?";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, "%" + subjectName + "%");
                resultSet = preStm.executeQuery();
                if (resultSet.next()) {
                    countAll = resultSet.getLong("COUNT");
                }
            }
        } finally {
            closeConnection();
        }
        return countAll;
    }

    public List<Quiz> getListPagningQuizBySubjectNameWithAdmin(String subjectName, int indexPage, int pageSize) throws Exception {
        List<Quiz> listQuiz = null;
        try {
            String sql = "Select quizId, quiz.subjectId, userId, quizTime, questionTotal, maxPoint, "
                    + "pointUser, createDate from Quiz quiz join Subject subject on "
                    + "quiz.subjectId = subject.subjectId \n"
                    + "where subjectName like ? \n"
                    + "order by createDate desc offset ? rows fetch next ? rows only";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, "%" + subjectName + "%");
                preStm.setInt(2, indexPage);
                preStm.setInt(3, pageSize);
                resultSet = preStm.executeQuery();
                listQuiz = new ArrayList<>();
                while (resultSet.next()) {
                    String getQuizId = resultSet.getString("quizId");
                    String getSubjectId = resultSet.getString("subjectId");
                    String userId = resultSet.getString("userId");
                    int quizTime = resultSet.getInt("quizTime");
                    int questionTotal = resultSet.getInt("questionTotal");
                    float maxPoint = resultSet.getFloat("maxPoint");
                    float pointUser = resultSet.getFloat("pointUser");
                    Date date = new Date(resultSet.getTimestamp("createDate").getTime());
                    Quiz quiz = new Quiz();
                    quiz.setQuizId(getQuizId);
                    quiz.setSubjectId(getSubjectId);
                    quiz.setUserId(userId);
                    quiz.setMaxPoint(maxPoint);
                    quiz.setQuestionTotal(questionTotal);
                    quiz.setQuizTime(quizTime);
                    quiz.setMaxPoint(maxPoint);
                    quiz.setPointUser(pointUser);
                    quiz.setCreateDate(date);
                    listQuiz.add(quiz);
                }
            }
        } finally {
            closeConnection();
        }
        return listQuiz;
    }

    public String getLastQuiz() throws Exception {
        String quizId = null;
        try {
            String sql = "select quizId from Quiz where createDate = (select max(createDate) from Quiz)";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                resultSet = preStm.executeQuery();
                while (resultSet.next()) {
                    quizId = resultSet.getString("quizId");
                }
            }
        } finally {
            closeConnection();
        }
        return quizId;
    }

    public boolean createNewQuiz(Quiz quiz) throws Exception {
        boolean checkSuccess = false;
        String sql = "insert into Quiz(quizId, userId, subjectId, quizTime, "
                + "questionTotal, maxPoint, pointUser, createDate, status)\n"
                + " values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, quiz.getQuizId());
                preStm.setString(2, quiz.getUserId());
                preStm.setString(3, quiz.getSubjectId());
                preStm.setInt(4, quiz.getQuizTime());
                preStm.setInt(5, quiz.getQuestionTotal());
                preStm.setFloat(6, quiz.getMaxPoint());
                preStm.setFloat(7, quiz.getPointUser());
                preStm.setTimestamp(8, new Timestamp(quiz.getCreateDate().getTime()));
                preStm.setInt(9, quiz.getStatus());
                checkSuccess = preStm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return checkSuccess;
    }

    public boolean updateQuizByQuizExisted(Quiz quiz) throws Exception {
        boolean checkSuccess = false;
        try {
            String sql = "Update Quiz "
                    + "SET pointUser = ? "
                    + "Where quizId = ?";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                connection.setAutoCommit(false);
                preStm = connection.prepareStatement(sql);
                preStm.setFloat(1, quiz.getPointUser());
                preStm.setString(2, quiz.getQuizId());
                checkSuccess = preStm.executeUpdate() > 0;
                connection.commit();
            }
        } finally {
            closeConnection();
        }
        return checkSuccess;
    }
}
