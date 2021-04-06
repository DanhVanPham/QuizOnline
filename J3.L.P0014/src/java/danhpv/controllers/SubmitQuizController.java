/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.controllers;

import danhpv.daos.AnswerDAO;
import danhpv.daos.AnswerUserDAO;
import danhpv.daos.QuizDAO;
import danhpv.daos.SubjectDAO;
import danhpv.dtos.AnswerUser;
import danhpv.dtos.CartShopping;
import danhpv.dtos.Quiz;
import danhpv.dtos.Subject;
import java.io.IOException;
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
public class SubmitQuizController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "viewResultQuiz.jsp";
    private static final Logger LOGGER = Logger.getLogger(SubmitQuizController.class);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            CartShopping cartShopping = (CartShopping) session.getAttribute("cart");
            if (cartShopping != null) {
                AnswerUserDAO answerUserDao = new AnswerUserDAO();
                AnswerDAO answerDao = new AnswerDAO();
                QuizDAO quizDao = new QuizDAO();
                String quizId = request.getParameter("quizId");
                Quiz quiz = quizDao.getQuizByQuizId(quizId);
                List<AnswerUser> listAnswerUser = answerUserDao.getListAnswerByQuizId(quizId);
                if (listAnswerUser != null && !listAnswerUser.isEmpty()) {
                    for (AnswerUser answerUser : listAnswerUser) {
                        if (cartShopping.getCart().containsKey(answerUser.getAnswerUserId())) {
                            answerUser.setAnswerUser(cartShopping.getCart().get(answerUser.getAnswerUserId()));
                            boolean isCorrect = answerDao.getAnswerIsCorrectByAnswerId(answerUser.getAnswerId());
                            if(isCorrect) {
                                answerUser.setIsCorrect(true);
                            }else {
                                answerUser.setIsCorrect(false);
                            }
                            answerUserDao.updateAnswerUserByAnswerExisted(answerUser);
                        }
                    }
                    Subject subject = new SubjectDAO().getSubjectBySubjectId(quiz.getSubjectId());
                    int countAnswerCorrect = answerUserDao.countTotalAnswerUserCorrectByQuizId(quiz.getQuizId());
                    quiz.setPointUser(countAnswerCorrect * subject.getPointPerQuestion());
                    quizDao.updateQuizByQuizExisted(quiz);
                    request.setAttribute("questionCorrect", countAnswerCorrect);
                    request.setAttribute("result", quiz);
                    url = SUCCESS;
                    session.removeAttribute("TIME");
                    session.removeAttribute("cart");
                } else {
                    request.setAttribute("ERROR", "Can not found list question!");
                }
            } else {
                request.setAttribute("ERROR", "Can not found list answer to submit!");
            }
        } catch (Exception e) {
            LOGGER.error("Error at SubmitQuizController: " + e.getMessage());
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
