/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.controllers;

import danhpv.daos.AnswerUserDAO;
import danhpv.daos.QuizDAO;
import danhpv.dtos.AnswerUser;
import danhpv.dtos.Quiz;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author DELL
 */
public class ViewQuizDetailController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "viewQuizDetails.jsp";
    private static final Logger LOGGER = Logger.getLogger(ViewQuizDetailController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String quizId = request.getParameter("quizId");
            QuizDAO quizDao = new QuizDAO();
            AnswerUserDAO answerUserDao = new AnswerUserDAO();
            Quiz checkQuiz = quizDao.getQuizByQuizId(quizId);
            if (checkQuiz != null) {
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
                long countAll = answerUserDao.getCountAllQuestionByQuizId(checkQuiz.getQuizId());
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
                    listAnswerUser = answerUserDao.getListPagningAnswerAfterSubmitByQuizId(checkQuiz.getQuizId(), (index - 1) * pageSize, pageSize);
                    request.setAttribute("ENDPAGE", endPage);
                    request.setAttribute("INDEX_PAGE", index);
                }
                if (listAnswerUser != null && !listAnswerUser.isEmpty()) {
                    url = SUCCESS;
                    request.setAttribute("LIST_ANSWER", listAnswerUser);
                    request.setAttribute("QUIZ", checkQuiz);
                } else {
                    request.setAttribute("ERROR", "List answer is not found by quiz id!");
                }
            } else {
                request.setAttribute("ERROR", "Can not found quiz by quiz id!");
            }
        } catch (Exception e) {
            LOGGER.error("Error at ViewQuizDetailController: " + e.getMessage());
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
