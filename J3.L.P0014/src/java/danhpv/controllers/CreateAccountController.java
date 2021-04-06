/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.controllers;

import danhpv.daos.UserDAO;
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
import org.apache.log4j.Logger;

/**
 *
 * @author DELL
 */
public class CreateAccountController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "login.jsp";
    private static final String INVALID = "formCreateAccount.jsp";
    private static final Logger LOGGER = Logger.getLogger(CreateAccountController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String url = ERROR;
        try {
            String txtEmail = request.getParameter("txtEmail").trim();
            String txtStudentName = request.getParameter("txtStudentName").trim();
            String txtPassword = request.getParameter("txtPassword").trim();
            boolean valid = true;
            UserError userError = new UserError();
            UserDAO userDao = new UserDAO();
            if (txtEmail == null || txtEmail.length() == 0) {
                valid = false;
                userError.setEmailError("Email is not null!");
            } else {
                if (userDao.checkEmailExisted(txtEmail) != null) {
                    valid = false;
                    userError.setEmailError("Email have been existed!");
                }else {
                    if(txtEmail.length() > 50) {
                        valid = false;
                        userError.setEmailError("Length of email must be smaller or equal 50!");
                    }
                }
            }
            if(txtStudentName == null || txtStudentName.length() == 0) {
                valid = false;
                userError.setNameError("Student name is not null!");
            }else {
                if(txtStudentName.length() > 50) {
                    valid = false;
                    userError.setNameError("Length of student name must be smaller or equal 50!");
                }
            }
            if(txtPassword == null || txtPassword.length() == 0) {
                valid = false;
                userError.setPasswordError("Password is not null!");
            }
            if (valid) {
                txtStudentName = new String(txtStudentName.getBytes("iso-8859-1"), "UTF-8");
                User newUser = new User(txtEmail, txtStudentName, "ROL_2", 1);
                boolean checkSuccess = userDao.createNewAccount(newUser, hashWith256(txtPassword));
                if (checkSuccess) {
                    url = SUCCESS;
                } else {
                    url = ERROR;
                    request.setAttribute("ERROR", "Create new account is failed!");
                }
            } else {
                url = INVALID;
                request.setAttribute("INVALID", userError);
            }
        } catch (Exception e) {
            LOGGER.error("Error at CreateAccountController: " + e.getMessage());
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
