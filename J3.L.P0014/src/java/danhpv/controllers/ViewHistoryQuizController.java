/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.controllers;

import danhpv.daos.QuizDAO;
import danhpv.daos.RoleDAO;
import danhpv.dtos.Quiz;
import danhpv.dtos.Role;
import danhpv.dtos.User;
import java.io.IOException;
import java.util.ArrayList;
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
public class ViewHistoryQuizController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "viewHistoryQuiz.jsp";
    private static final Logger LOGGER = Logger.getLogger(ViewHistoryQuizController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("USER");
            RoleDAO roleDao = new RoleDAO();
            QuizDAO quizDao = new QuizDAO();
            Role checkRole = roleDao.getRoleByRoleId(user.getRoleId());
            if (checkRole == null) {
                request.setAttribute("ERROR", "Required login before get history quiz!");
            } else {
                String txtSearchBySubjectName;
                txtSearchBySubjectName = request.getParameter("txtSearchBySubjectName");
                if (txtSearchBySubjectName == null) {
                    txtSearchBySubjectName = "";
                } else {
                    if (txtSearchBySubjectName.length() == 0) {
                        txtSearchBySubjectName = "";
                    }
                }

                List<Quiz> listPaging;
                int index = 1;
                String getIndex = request.getParameter("pageIndex");
                if (getIndex != null) {
                    try {
                        index = Integer.parseInt(getIndex);
                    } catch (NumberFormatException e) {
                        index = 1;
                    }
                }
                if (checkRole.getRoleName().equalsIgnoreCase("Student")) {
                    long countAll = quizDao.getCountAllQuizByUserIdAndSubjectName(user.getEmail(), txtSearchBySubjectName);
                    int count = (int) countAll;
                    if (count == 0) {
                        listPaging = new ArrayList<>();
                    } else {
                        int pageSize = 3;
                        int endPage;
                        endPage = count / pageSize;
                        if (count % pageSize != 0) {
                            endPage++;
                        }
                        listPaging = quizDao.getListPagningQuizByUserIdAndSubjectName(user.getEmail(),
                                txtSearchBySubjectName, (index - 1) * pageSize, pageSize);
                        request.setAttribute("ENDPAGE", endPage);
                    }
                    request.setAttribute("LIST_HISTORY_QUIZ", listPaging);
                    url = SUCCESS;
                } else if (checkRole.getRoleName().equalsIgnoreCase("Admin")) {
                    long countAll = quizDao.getCountAllQuizBySubjectNameWithAdmin(txtSearchBySubjectName);
                    int count = (int) countAll;
                    if (count == 0) {
                        listPaging = new ArrayList<>();
                    } else {
                        int pageSize = 3;
                        int endPage;
                        endPage = count / pageSize;
                        if (count % pageSize != 0) {
                            endPage++;
                        }
                        listPaging = quizDao.getListPagningQuizBySubjectNameWithAdmin(txtSearchBySubjectName, (index - 1) * pageSize, pageSize);
                        request.setAttribute("ENDPAGE", endPage);
                    }
                    request.setAttribute("LIST_HISTORY_QUIZ", listPaging);
                    url = SUCCESS;
                } else {
                    request.setAttribute("ERROR", "Your role is invalid!");
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error at ViewHistoryQuizController: " + e.getMessage());
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
