/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.controllers;

import danhpv.daos.AnswerDAO;
import danhpv.daos.QuestionDAO;
import danhpv.dtos.Answer;
import danhpv.dtos.Question;
import java.io.IOException;
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
public class ViewFormUpdateQuestionController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "formUpdateQuestion.jsp";
    private static final Logger LOGGER = Logger.getLogger(ViewFormUpdateQuestionController.class);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String questionId = request.getParameter("questionId");
            QuestionDAO questionDao = new QuestionDAO();
            Question question = questionDao.getQuestionByQuestionId(questionId);
            if (question != null) {
                AnswerDAO answerDao = new AnswerDAO();
                List<Answer> listAnswers = answerDao.getListAnswerByQuestionId(questionId);
                request.setAttribute("QUESTION_DETAILS", question);
                request.setAttribute("ANSWER_DETAILS", listAnswers);
                url = SUCCESS;
            } else {
                request.setAttribute("ERROR", "Can not found question by question id!");
            }
        } catch (Exception e) {
            LOGGER.error("Error at ViewFormUpdateQuestionController: " + e.getMessage());
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
