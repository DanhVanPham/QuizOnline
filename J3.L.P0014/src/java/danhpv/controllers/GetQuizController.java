/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.controllers;

import danhpv.daos.AnswerUserDAO;
import danhpv.daos.QuestionDAO;
import danhpv.daos.QuizDAO;
import danhpv.daos.RoleDAO;
import danhpv.daos.SubjectDAO;
import danhpv.dtos.AnswerUser;
import danhpv.dtos.CartShopping;
import danhpv.dtos.Question;
import danhpv.dtos.Quiz;
import danhpv.dtos.Role;
import danhpv.dtos.Subject;
import danhpv.dtos.User;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author DELL
 */
public class GetQuizController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "viewQuiz.jsp";
    private static final Logger LOGGER = Logger.getLogger(GetQuizController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            long oneMinuteInMillis = 60000;
            String subjectId = request.getParameter("subject");
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("USER");
            if (user != null) {
                QuestionDAO questionDao = new QuestionDAO();
                RoleDAO roleDao = new RoleDAO();
                QuizDAO quizDao = new QuizDAO();
                AnswerUserDAO answerUserDao = new AnswerUserDAO();
                SubjectDAO subjectDao = new SubjectDAO();
                List<Question> listQuestion;
                Role checkRole = roleDao.getRoleByRoleId(user.getRoleId());
                if (checkRole.getRoleName().equalsIgnoreCase("Admin")) {
                    request.setAttribute("ERROR", "Your role is invalid!");
                } else if (checkRole.getRoleName().equalsIgnoreCase("Student")) {
                    Subject checkSubject = subjectDao.getSubjectBySubjectId(subjectId);
                    if (checkSubject != null) {
                        listQuestion = questionDao.getListQuestionByRandomN(checkSubject.getTotalQuestion(), checkSubject.getSubjectId());
                        if (listQuestion == null || listQuestion.isEmpty()) {
                            request.setAttribute("ERROR", "Question is not found for quiz!");
                        } else {
                            if ((listQuestion.size()) == checkSubject.getTotalQuestion()) {
                                int countQuizId;
                                String quizId = quizDao.getLastQuiz();
                                if (quizId == null) {
                                    countQuizId = 1;
                                    quizId = "QUIZ-" + String.format("%05d", countQuizId);
                                } else {
                                    String[] split = quizId.split("-");
                                    countQuizId = Integer.parseInt(split[1]);
                                    countQuizId += 1;
                                    quizId = split[0] + "-" + String.format("%05d", countQuizId);
                                }
                                Quiz newQuiz = new Quiz(quizId, user.getEmail(), checkSubject.getSubjectId(),
                                        checkSubject.getTimeTakeQuiz(), listQuestion.size(),
                                        checkSubject.getPointPerQuestion() * checkSubject.getTotalQuestion(),
                                        0, new Date(), 1);
                                boolean checkSuccess = quizDao.createNewQuiz(newQuiz);
                                if (checkSuccess) {
                                    boolean checkCreateAnswer = answerUserDao.createNewAnswerUser(listQuestion, newQuiz);
                                    if (checkCreateAnswer) {
                                        List<AnswerUser> listAnswerUser;
                                        int index = 1;
                                        String getIndex = request.getParameter("pageIndex");
                                        if (getIndex != null) {
                                            try {
                                                index = Integer.parseInt(getIndex);
                                            } catch (NumberFormatException e) {
                                                index = 1;
                                            }
                                        }
                                        long countAll = answerUserDao.getCountAllQuestionByQuizId(newQuiz.getQuizId());
                                        int count = (int) countAll;
                                        if (count == 0) {
                                            listAnswerUser = new ArrayList<>();
                                        } else {
                                            int pageSize = 4;
                                            int endPage;
                                            if (countAll <= 3) {
                                                endPage = 1;
                                            } else {
                                                endPage = count / pageSize;
                                                if (count % pageSize != 0) {
                                                    endPage++;
                                                }
                                            }
                                            listAnswerUser = answerUserDao.getListPagingAnswerByQuizId(newQuiz.getQuizId(), (index - 1) * pageSize, pageSize);
                                            request.setAttribute("ENDPAGE", endPage);
                                            request.setAttribute("INDEX_PAGE", index);
                                        }
                                        url = SUCCESS;
                                        request.setAttribute("LIST_ANSWER", listAnswerUser);
                                        request.setAttribute("QUIZ", newQuiz);
                                        Calendar date = Calendar.getInstance();
                                        long time = date.getTimeInMillis();
                                        Date timeStart = new Date(time + (checkSubject.getTimeTakeQuiz() * oneMinuteInMillis));
                                        SimpleDateFormat format = new SimpleDateFormat("MM dd yyyy HH:mm:ss");
                                        String timestartParse = format.format(timeStart);
                                        session.setAttribute("TIME", timestartParse);
                                        CartShopping cartShopping = new CartShopping();
                                        session.setAttribute("cart", cartShopping);
                                    } else {
                                        request.setAttribute("ERROR", "Something is wrong. Can not get quiz!");
                                    }
                                } else {
                                    request.setAttribute("ERROR", "Something is wrong. Can not get quiz!");
                                }
                            } else {
                                request.setAttribute("ERROR", "Bank of question is not enought to create quiz!");
                            }
                        }
                    } else {
                        request.setAttribute("ERROR", "Can not get subject by subject id!");
                    }
                } else {
                    request.setAttribute("ERROR", "Your role is invalid!");
                }
            } else {
                request.setAttribute("ERROR", "Required login before get quiz!");
            }
        } catch (Exception e) {
            LOGGER.error("Error at GetQuizController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
