/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.controllers;

import danhpv.daos.RoleDAO;
import danhpv.daos.UserDAO;
import danhpv.dtos.Role;
import danhpv.dtos.User;
import danhpv.dtos.UserError;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
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
public class LoginController extends HttpServlet {

    private static final String INVALID = "login.jsp";
    private static final String USER = "formSelectQuiz.jsp";
    private static final String ADMIN = "ViewQuestionByAdminController";
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = INVALID;
        try {
            String email = request.getParameter("txtEmail");
            String password = request.getParameter("txtPassword");
            UserError error = new UserError();
            boolean valid = true;
            if (email.length() == 0) {
                valid = false;
                error.setEmailError("Email is not null!");
            }
            if (password.length() == 0) {
                valid = false;
                error.setPasswordError("Password is not null!");
            }
            if (valid) {
                UserDAO userDao = new UserDAO();
                RoleDAO roleDao = new RoleDAO();
                User userDTO = userDao.checkLogin(email, hashWith256(password));
                if (userDTO != null) {
                    if (userDTO.getStatus() == 0) {
                        request.setAttribute("ERROR", "Your account have been blocked!");
                    } else {
                        Role role = roleDao.getRoleByRoleId(userDTO.getRoleId());
                        if (role != null) {
                            HttpSession session = request.getSession();
                            session.setAttribute("USER", userDTO);
                            if (role.getRoleName().equalsIgnoreCase("Admin")) {
                                url = ADMIN;
                            } else if (role.getRoleName().equalsIgnoreCase("Student")) {
                                url = USER;
                            } else {
                                request.setAttribute("ERROR", "Your role is invalid!");
                            }
                        } else {
                            request.setAttribute("ERROR", "Your role is invalid!");
                        }
                    }
                } else {
                    request.setAttribute("ERROR", "Username or password is not correct!");
                }
            } else {
                request.setAttribute("INVALID", error);
            }
        } catch (Exception e) {
            request.setAttribute("ERROR", "Username or password is not correct!");
            LOGGER.error("Error at LoginController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    String hashWith256(String textToHash) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] byteOfTextToHash = textToHash.getBytes(StandardCharsets.UTF_8);
        byte[] hashedByetArray = digest.digest(byteOfTextToHash);
        String encoded = Base64.getEncoder().encodeToString(hashedByetArray);
        return encoded;
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
