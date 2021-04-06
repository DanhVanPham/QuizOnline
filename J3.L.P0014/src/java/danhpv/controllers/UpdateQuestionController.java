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
import danhpv.dtos.QuestionError;
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
public class UpdateQuestionController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "ViewQuestionByAdminController";
    private static final Logger LOGGER = Logger.getLogger(UpdateQuestionController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String txtQuestionId = request.getParameter("txtQuestionId").trim();
            String subjectId = request.getParameter("subject").trim();
            String txtAreaContent = request.getParameter("txtAreaContent").trim();
            String txtAnswerA = request.getParameter("txtAnswerA").trim();
            String txtAnswerB = request.getParameter("txtAnswerB").trim();
            String txtAnswerC = request.getParameter("txtAnswerC").trim();
            String txtAnswerD = request.getParameter("txtAnswerD").trim();
            String answerCorrect = request.getParameter("txtAnswerCorrect");
            QuestionError questionError = new QuestionError();
            boolean valid = true;
            if (subjectId == null || subjectId.length() == 0) {
                questionError.setSubjectIdError("Subject is not null!");
                valid = false;
            }
            if (txtAnswerA == null || txtAnswerA.length() == 0) {
                questionError.setaAnswerError("Required input answer A!");
                valid = false;
            }
            if (txtAnswerB == null || txtAnswerB.length() == 0) {
                questionError.setbAnswerError("Required input answer B!");
                valid = false;
            }
            if (txtAnswerC == null || txtAnswerC.length() == 0) {
                questionError.setcAnswerError("Required input answer c!");
                valid = false;
            }
            if (txtAnswerD == null || txtAnswerD.length() == 0) {
                questionError.setdAnswerError("Required input answer D!");
                valid = false;
            }
            if (answerCorrect == null || answerCorrect.length() == 0) {
                questionError.setAnswerCorrectError("Required select answer correct!");
                valid = false;
            }
            if (txtAreaContent == null || txtAreaContent.length() == 0) {
                questionError.setQuestionContent("Content of question is not null!");
                valid = false;
            }
            if (valid) {
                QuestionDAO questionDao = new QuestionDAO();
                Question checkQuestion = questionDao.getQuestionByQuestionId(txtQuestionId);
                if (checkQuestion != null && checkQuestion.getStatus() == 1) {
                    checkQuestion.setSubjectId(subjectId);
                    checkQuestion.setQuestionContent(txtAreaContent);
                    boolean checkSuccess = questionDao.updateQuestionByQuestionExisted(checkQuestion);
                    if (checkSuccess) {
                        AnswerDAO answerDao = new AnswerDAO();
                        String listAnswerUpdate[] = {txtAnswerA, txtAnswerB, txtAnswerC, txtAnswerD};
                        List<Answer> listAnswers = answerDao.getListAnswerByQuestionId(txtQuestionId);
                        for (int i = 0; i < listAnswers.size(); i++) {
                            listAnswers.get(i).setAnswerContent(listAnswerUpdate[i]);
                            if (answerCorrect != null && answerCorrect.equalsIgnoreCase(listAnswers.get(i).getAnswerContent())) {
                                listAnswers.get(i).setType(true);
                            } else {
                                listAnswers.get(i).setType(false);
                            }
                            answerDao.updateAnswerByAnswerExisted(listAnswers.get(i));
                        }
                        url = SUCCESS;
                    } else {
                        request.setAttribute("ERROR", "Update question is failed!");
                    }
                } else {
                    request.setAttribute("ERROR", "Question is not found!");
                }
            } else {
                request.setAttribute("ERROR", "Required input and select all field!");
            }
        } catch (Exception e) {
            LOGGER.error("Error at UpdateQuestionController: " + e.getMessage());
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
