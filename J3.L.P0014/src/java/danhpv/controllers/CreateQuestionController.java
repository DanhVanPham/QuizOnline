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
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author DELL
 */
public class CreateQuestionController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String INVALID = "formCreateQuestion.jsp";
    private static final String SUCCESS = "ViewQuestionByAdminController";
    private static final Logger LOGGER = Logger.getLogger(CreateQuestionController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String subject = request.getParameter("subject").trim();
            String txtAreaContent = request.getParameter("txtAreaContent").trim();
            String txtAnswerA = request.getParameter("txtAnswerA").trim();
            String txtAnswerB = request.getParameter("txtAnswerB").trim();
            String txtAnswerC = request.getParameter("txtAnswerC").trim();
            String txtAnswerD = request.getParameter("txtAnswerD").trim();
            String txtAnswerCorrect = request.getParameter("txtAnswerCorrect").trim();
            QuestionError questionError = new QuestionError();
            boolean valid = true;
            if (subject == null || subject.length() == 0) {
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
            if (txtAnswerCorrect == null || txtAnswerCorrect.length() == 0) {
                questionError.setAnswerCorrectError("Required select answer correct!");
                valid = false;
            }
            if (txtAreaContent == null || txtAreaContent.length() == 0) {
                questionError.setQuestionContent("Content of question is not null!");
                valid = false;
            }
            if (valid) {
                QuestionDAO questionDao = new QuestionDAO();
                AnswerDAO answerDao = new AnswerDAO();
                String questionIdNewest = questionDao.getLastQuestionAdded();
                int count;
                if (questionIdNewest == null) {
                    count = 1;
                    questionIdNewest = "QUESTION-" + String.format("%05d", count);
                } else {
                    String[] split = questionIdNewest.split("-");
                    try {
                        count = Integer.parseInt(split[split.length - 1]);
                        count += 1;
                        questionIdNewest = "QUESTION-" + String.format("%05d", count);
                    } catch (NumberFormatException e) {
                        log("Error at CreateFoodController: " + e.getMessage());
                    }
                }
                Question question = new Question(questionIdNewest, txtAreaContent, new Date(), subject, 1);
                boolean checkSuccess = questionDao.createNewQuestion(question);
                if (checkSuccess) {
                    String listAnswer[] = {txtAnswerA, txtAnswerB, txtAnswerC, txtAnswerD};
                    for (int i = 1; i < 5; i++) {
                        String answerId = questionIdNewest + '-' + i;
                        Answer answer;
                        if (listAnswer[i - 1].equalsIgnoreCase(txtAnswerCorrect)) {
                            answer = new Answer(answerId, listAnswer[i - 1], questionIdNewest, true);
                        }else {
                            answer = new Answer(answerId, listAnswer[i - 1], questionIdNewest, false);
                        }
                        answerDao.createNewAnswer(answer);
                    }
                    url = SUCCESS;
                } else {
                    url = ERROR;
                    request.setAttribute("ERROR", "Create new quiz is failed!");
                }
            } else {
                url = INVALID;
                request.setAttribute("INVALID", questionError);
            }
        } catch (Exception e) {
            LOGGER.error("Error at CreateQuizController: " + e.getMessage());
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
