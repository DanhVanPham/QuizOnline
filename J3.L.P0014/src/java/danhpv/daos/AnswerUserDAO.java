/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.daos;

import danhpv.db.MyConnection;
import danhpv.dtos.Answer;
import danhpv.dtos.AnswerUser;
import danhpv.dtos.Question;
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
public class AnswerUserDAO {

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

    public AnswerUserDAO() {
    }

    public List<String> getLastAnswerUser() throws Exception {
        List<String> listAnswerUserId = null;
        String answerUserId;
        try {
            String sql = "select answerUserId from AnswerUser where createDate = (select max(createDate) from AnswerUser)";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                resultSet = preStm.executeQuery();
                listAnswerUserId = new ArrayList<>();
                while (resultSet.next()) {
                    answerUserId = resultSet.getString("answerUserId");
                    listAnswerUserId.add(answerUserId);
                }
            }
        } finally {
            closeConnection();
        }
        return listAnswerUserId;
    }

    public List<String> getListAnswerUserIdWithQuestionIdAndQuizId(String questionId, String quizId) throws Exception {
        List<String> listAnswerUserId = null;
        String answerUserId;
        try {
            String sql = "select answerUserId from AnswerUser where answerId like ? and quizId = ?";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, questionId + "-%");
                preStm.setString(2, quizId);
                resultSet = preStm.executeQuery();
                listAnswerUserId = new ArrayList<>();
                while (resultSet.next()) {
                    answerUserId = resultSet.getString("answerUserId");
                    listAnswerUserId.add(answerUserId);
                }
            }
        } finally {
            closeConnection();
        }
        return listAnswerUserId;
    }

    public boolean createNewAnswerUser(List<Question> listQuestion, Quiz quiz) throws Exception {
        int countAnswerId;
        String answerUserId;
        for (Question question : listQuestion) {
            AnswerDAO answerDao = new AnswerDAO();
            List<Answer> listAnswer = answerDao.getListAnswerByQuestionId(question.getQuestionId());
            for (Answer answer : listAnswer) {
                List<String> listLastAnswerUserId = getLastAnswerUser();
                if (listLastAnswerUserId == null || listLastAnswerUserId.isEmpty()) {
                    countAnswerId = 1;
                    answerUserId = "ANSWER-" + String.format("%05d", countAnswerId);
                } else {
                    String[] split = listLastAnswerUserId.get(listLastAnswerUserId.size() - 1).split("-");
                    countAnswerId = Integer.parseInt(split[1]);
                    countAnswerId += 1;
                    answerUserId = split[0] + "-" + String.format("%05d", countAnswerId);
                }
                AnswerUser newAnswerUser = new AnswerUser(answerUserId, question.getQuestionId(), quiz.getQuizId(),
                        null, new Date(), false);
                newAnswerUser.setAnswerId(answer.getAnswerId());
                createNewAnswerUser(newAnswerUser);
            }
        }

        return true;
    }

    public boolean createNewAnswerUser(AnswerUser answerUser) throws Exception {
        boolean checkSuccess = false;
        try {
            String sql = "Insert into AnswerUser(answerUserId, questionId, quizId, answerId, "
                    + "answer_user, createDate, isCorrect) \n"
                    + "      values(?, ?, ?, ?, ?, ?, ?)";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, answerUser.getAnswerUserId());
                preStm.setString(2, answerUser.getQuestionId());
                preStm.setString(3, answerUser.getQuizId());
                preStm.setString(4, answerUser.getAnswerId());
                preStm.setString(5, answerUser.getAnswerUser());
                preStm.setTimestamp(6, new Timestamp(answerUser.getCreateDate().getTime()));
                preStm.setBoolean(7, answerUser.isIsCorrect());
                checkSuccess = preStm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return checkSuccess;
    }

    public long getCountAllQuestionByQuizId(String quizId) throws Exception {
        long count = 0;
        String sql = "select count(answerUserId) as 'COUNT' from AnswerUser where quizId = ?";
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

    public List<AnswerUser> getListPagingAnswerByQuizId(String quizId, int indexStart, int pageSize) throws Exception {
        List<AnswerUser> listAnswerUser = null;
        String sql = "select answerUserId, answer.answerId,question.question_content, answer.answerContent, answerUser.questionId\n"
                + "from AnswerUser answerUser join Question question on answerUser.questionId = question.questionId\n"
                + "	join Answer answer on answer.answerId = answerUser.answerId\n"
                + "	where answerUser.answerUserId in (\n"
                + "select answerUserId from  AnswerUser where answerUser.quizId = ?) and status = ? order by \n"
                + "     answerUser.createDate ASC offset ? rows fetch next ? rows only";
        try {
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, quizId);
                preStm.setInt(2, 1);
                preStm.setInt(3, indexStart);
                preStm.setInt(4, pageSize);
                resultSet = preStm.executeQuery();
                listAnswerUser = new ArrayList<>();
                while (resultSet.next()) {
                    String answerUserId = resultSet.getString("answerUserId");
                    String answerId = resultSet.getString("answerId");
                    String questionContent = resultSet.getString("question_content");
                    String answerContent = resultSet.getString("answerContent");
                    String questionId = resultSet.getString("questionId");
                    AnswerUser answerUser = new AnswerUser();
                    answerUser.setAnswerUserId(answerUserId);
                    answerUser.setAnswerId(answerId);
                    answerUser.setQuizId(quizId);
                    answerUser.setQuestionId(questionId);
                    answerUser.setAnswerContent(answerContent);
                    answerUser.setQuestionContent(questionContent);
                    listAnswerUser.add(answerUser);
                }
            }
        } finally {
            closeConnection();
        }
        return listAnswerUser;
    }

    public List<AnswerUser> getListAnswerByQuizId(String quizId) throws Exception {
        List<AnswerUser> listAnswerUser = null;
        String sql = "select answerUserId, question.question_content, answer.answerContent, \n"
                + "    answerUser.questionId, answer.type, answerUser.answerId from \n"
                + "	AnswerUser answerUser join Answer answer on answerUser.answerId = answer.answerId\n"
                + "	join Question question on answerUser.questionId = question.questionId\n"
                + "	where quizId = ?";
        try {
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, quizId);
                resultSet = preStm.executeQuery();
                listAnswerUser = new ArrayList<>();
                while (resultSet.next()) {
                    String answerUserId = resultSet.getString("answerUserId");
                    String questionContent = resultSet.getString("question_content");
                    String answerContent = resultSet.getString("answerContent");
                    String questionId = resultSet.getString("questionId");
                    boolean isCorrect = resultSet.getBoolean("type");
                    String answerId = resultSet.getString("answerId");
                    AnswerUser answerUser = new AnswerUser();
                    answerUser.setAnswerUserId(answerUserId);
                    answerUser.setQuestionContent(questionContent);
                    answerUser.setAnswerContent(answerContent);
                    answerUser.setIsCorrect(isCorrect);
                    answerUser.setQuestionId(questionId);
                    answerUser.setAnswerId(answerId);
                    listAnswerUser.add(answerUser);
                }
            }
        } finally {
            closeConnection();
        }
        return listAnswerUser;
    }

    public List<AnswerUser> getListPagningAnswerAfterSubmitByQuizId(String quizId, int indexStart, int pageSize) throws Exception {
        List<AnswerUser> listAnswerUser = null;
        String sql = "select answerUserId, question.question_content, answer.answerContent, answerUser.answer_user,\n"
                + "    answerUser.questionId, answer.type, answerUser.answerId from \n"
                + "	AnswerUser answerUser join Answer answer on answerUser.answerId = answer.answerId\n"
                + "	join Question question on answerUser.questionId = question.questionId\n"
                + "	where quizId = ? order by answerUser.createDate asc offset ? rows fetch next ? rows only";
        try {
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, quizId);
                preStm.setInt(2, indexStart);
                preStm.setInt(3, pageSize);
                resultSet = preStm.executeQuery();
                listAnswerUser = new ArrayList<>();
                while (resultSet.next()) {
                    String answerUserId = resultSet.getString("answerUserId");
                    String questionContent = resultSet.getString("question_content");
                    String answerContent = resultSet.getString("answerContent");
                    String answUser = resultSet.getString("answer_user");
                    String questionId = resultSet.getString("questionId");
                    boolean isCorrect = resultSet.getBoolean("type");
                    String answerId = resultSet.getString("answerId");
                    AnswerUser answerUser = new AnswerUser();
                    answerUser.setAnswerUserId(answerUserId);
                    answerUser.setQuestionContent(questionContent);
                    answerUser.setAnswerContent(answerContent);
                    answerUser.setIsCorrect(isCorrect);
                    answerUser.setQuestionId(questionId);
                    answerUser.setAnswerId(answerId);
                    answerUser.setAnswerUser(answUser);
                    listAnswerUser.add(answerUser);
                }
            }
        } finally {
            closeConnection();
        }
        return listAnswerUser;
    }

    public boolean updateAnswerUserByAnswerExisted(AnswerUser answerUser) throws Exception {
        boolean checkSuccess = false;
        try {
            String sql = "Update AnswerUser "
                    + "SET answer_user = ?,  isCorrect = ? "
                    + "Where answerUserId = ?";
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, answerUser.getAnswerUser());
                preStm.setBoolean(2, answerUser.isIsCorrect());
                preStm.setString(3, answerUser.getAnswerUserId());
                checkSuccess = preStm.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return checkSuccess;
    }

    public int countTotalAnswerUserCorrectByQuizId(String quizId) throws Exception {
        int countCorrect = 0;
        String sql = "select count(answerUserId) as 'COUNT'\n"
                + "from AnswerUser\n"
                + "where quizId = ? and isCorrect = ?";
        try {
            connection = MyConnection.getMyConnection();
            if (connection != null) {
                preStm = connection.prepareStatement(sql);
                preStm.setString(1, quizId);
                preStm.setInt(2, 1);
                resultSet = preStm.executeQuery();
                if (resultSet.next()) {
                    countCorrect = resultSet.getInt("COUNT");
                }
            }
        } finally {
            closeConnection();
        }
        return countCorrect;
    }
}
