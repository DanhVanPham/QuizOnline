/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.controllers;

import danhpv.daos.AnswerUserDAO;
import danhpv.dtos.CartShopping;
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
public class AddAnswerToCartController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "ViewQuestionInQuizController";
    private static final Logger LOGGER = Logger.getLogger(AddAnswerToCartController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String answerUserId = request.getParameter("answerUserId");
            String quizId = request.getParameter("quizId");
            String answerId = request.getParameter("answerId");
            String answerSelect = request.getParameter("checkRadioAnswer");
            AnswerUserDAO answerUserDao = new AnswerUserDAO();
            HttpSession session = request.getSession();
            CartShopping cartShopping = (CartShopping) session.getAttribute("cart");
            if (cartShopping == null) {
                cartShopping = new CartShopping();
            }
            String split[] = answerId.split("-");
            String questionId = split[0] + "-" + split[1];
            List<String> listAnswerUserId = answerUserDao.getListAnswerUserIdWithQuestionIdAndQuizId(questionId, quizId);
            if (cartShopping.addCart(answerUserId, answerSelect, listAnswerUserId)) {
                session.setAttribute("cart", cartShopping);
                url = SUCCESS;
            } else {
                request.setAttribute("ERROR", "Some thing is wrong!");
            }
        } catch (Exception e) {
            LOGGER.error("Error at AddAnswerToCartController: " + e.getMessage());
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
