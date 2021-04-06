/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.controllers;

import danhpv.daos.QuestionDAO;
import danhpv.daos.RoleDAO;
import danhpv.dtos.Question;
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
public class ViewQuestionByAdminController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "manageQuiz.jsp";
    private static final Logger LOGGER = Logger.getLogger(ViewQuestionByAdminController.class);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String txtSearchByQuestionName = request.getParameter("txtSearchByQuestionName");
            String checkSubject = request.getParameter("checkRadioSubject");
            String checkStatus = request.getParameter("checkRadioStatus");
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("USER");
            RoleDAO roleDao = new RoleDAO();
            QuestionDAO questionDao = new QuestionDAO();
            Role checkRole = null;
            if (user != null) {
                String roleId = user.getRoleId();
                checkRole = roleDao.getRoleByRoleId(roleId);
            }
            int index = 1;
            String getIndex = request.getParameter("pageIndex");
            if (getIndex != null) {
                try {
                    index = Integer.parseInt(getIndex);
                } catch (NumberFormatException e) {
                    index = 1;
                }
            }
            List<Question> listPaging;
            if (txtSearchByQuestionName != null) {
                if (txtSearchByQuestionName.length() == 0) {
                    txtSearchByQuestionName = "";
                }
            }else {
                txtSearchByQuestionName = "";
            }
            if (checkSubject != null && checkSubject.length() != 0) {
                if (checkSubject.equals("All")) {
                    checkSubject = null;
                }
            } else {
                if (checkSubject != null && checkSubject.length() == 0) {
                    checkSubject = null;
                }
            }
            if (checkStatus != null && checkStatus.length() != 0) {
                switch (checkStatus) {
                    case "All":
                        checkStatus = null;
                        break;
                    case "selectedTrue":
                        checkStatus = "1";
                        break;
                    case "selectedFalse":
                        checkStatus = "0";
                        break;
                    default:
                        break;
                }
            } else {
                if (checkStatus != null && checkStatus.length() == 0) {
                    checkStatus = null;
                }
            }
            //tìm số lượng cần phân trang
            long countAll = questionDao.getCountAllQuestionBySearchWithAdmin(txtSearchByQuestionName, checkSubject, checkStatus);
            int count = (int) countAll;
            if (count == 0) {
                listPaging = new ArrayList<>();
            } else {
                int pageSize = 8;
                int endPage;
                endPage = count / pageSize;
                if (count % pageSize != 0) {
                    endPage++;
                }
                listPaging = questionDao.getListPagingQuestionBySearchWithAdmin(txtSearchByQuestionName, checkSubject, checkStatus, (index - 1) * pageSize, pageSize);
                request.setAttribute("ENDPAGE", endPage);
            }
            if (user != null && checkRole != null) {
                if (checkRole.getRoleName().equalsIgnoreCase("Admin")) {
                    url = SUCCESS;
                    request.setAttribute("LISTQUESTION", listPaging);
                } else {
                    request.setAttribute("ERROR", "Your role is invalid!");
                    url = ERROR;
                }
            } else {
                request.setAttribute("ERROR", "Your role is invalid!");
                url = ERROR;
            }
        } catch (Exception e) {
            LOGGER.error("Error at ViewQuizByAdminController: " + e.getMessage());
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
