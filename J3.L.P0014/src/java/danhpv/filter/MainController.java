/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.filter;

import danhpv.dtos.User;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
public class MainController implements Filter {
    
    private static final boolean debug = true;
    private static final String LOGIN = "/login.jsp";
    private static final String ROLE_ADMIN = "ROL_1";
    private static final String ROLE_STUDENT = "ROL_2";
    private static final String HOME_ADMIN = "ViewQuestionByAdminController";
    private static final String HOME_STUDENT = "formSelectQuiz.jsp";
    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public MainController() {
    }
    
    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("MainController:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }
    
    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("MainController:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String url = LOGIN;
        String uri = req.getRequestURI();
        int lastIndex = uri.lastIndexOf("/");
        String resource = uri.substring(lastIndex + 1);
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("USER");
        //xu ly urn
        try {
            if (resource.length() > 0) {
                url = resource;
                if (resource.endsWith(".css") || resource.endsWith(".js") || resource.endsWith(".jpg")) {
                    url = null;
                }
            }
            if (user != null && url != null) {
                if (user.getRoleId().equalsIgnoreCase(ROLE_ADMIN)) {
                    if (resource.equalsIgnoreCase("formSelectQuiz.jsp")) {
                        url = HOME_ADMIN;
                    } else if (resource.equalsIgnoreCase("formUpdateQuestion.jsp")) {
                        url = HOME_ADMIN;
                    } else if (resource.equalsIgnoreCase("login.jsp")) {
                        url = HOME_ADMIN;
                    } else if (resource.equalsIgnoreCase("manageQuiz.jsp")) {
                        url = HOME_ADMIN;
                    } else if (resource.equalsIgnoreCase("menu.jsp")) {
                        url = HOME_ADMIN;
                    } else if (resource.equalsIgnoreCase("viewHistoryQuiz.jsp")) {
                        url = "ViewHistoryQuizController";
                    } else if (resource.equalsIgnoreCase("viewQuestionDetails.jsp")) {
                        url = HOME_ADMIN;
                    } else if (resource.equalsIgnoreCase("viewQuiz.jsp")) {
                        url = HOME_ADMIN;
                    } else if (resource.equalsIgnoreCase("viewQuizDetails.jsp")) {
                        url = HOME_ADMIN;
                    } else if (resource.equalsIgnoreCase("viewResultQuiz.jsp")) {
                        url = HOME_ADMIN;
                    } else {
                        if (resource.equalsIgnoreCase("AddAnswerToCartController")) {
                            url = HOME_ADMIN;
                        } else if (resource.equalsIgnoreCase("CreateAccountController")) {
                            url = HOME_ADMIN;
                        } else if (resource.equalsIgnoreCase("GetQuizController")) {
                            url = HOME_ADMIN;
                        } else if (resource.equalsIgnoreCase("LoginController")) {
                            url = HOME_ADMIN;
                        } else if (resource.equalsIgnoreCase("SubmitQuizController")) {
                            url = HOME_ADMIN;
                        } else if (resource.equalsIgnoreCase("ViewQuestionInQuizController")) {
                            url = HOME_ADMIN;
                        }
                    }
                } else if (user.getRoleId().equalsIgnoreCase(ROLE_STUDENT)) {
                    if (resource.equalsIgnoreCase("formCreateAccount.jsp")) {
                        url = HOME_STUDENT;
                    } else if (resource.equalsIgnoreCase("formCreateQuestion.jsp")) {
                        url = HOME_STUDENT;
                    } else if (resource.equalsIgnoreCase("formUpdateQuestion.jsp")) {
                        url = HOME_STUDENT;
                    } else if (resource.equalsIgnoreCase("login.jsp")) {
                        url = HOME_STUDENT;
                    } else if (resource.equalsIgnoreCase("manageQuiz.jsp")) {
                        url = HOME_STUDENT;
                    } else if (resource.equalsIgnoreCase("navBarAdmin.jsp")) {
                        url = HOME_STUDENT;
                    } else if (resource.equalsIgnoreCase("viewHistoryQuiz.jsp")) {
                        url = "ViewHistoryQuizController";
                    } else if (resource.equalsIgnoreCase("viewQuestionDetails.jsp")) {
                        url = HOME_STUDENT;
                    } else if (resource.equalsIgnoreCase("viewQuiz.jsp")) {
                        url = "formSelectQuiz.jsp";
                    } else if (resource.equalsIgnoreCase("viewQuizDetails.jsp")) {
                        url = "ViewHistoryQuizController";
                    } else if (resource.equalsIgnoreCase("viewResultQuiz.jsp")) {
                        url = HOME_STUDENT;
                    } else {
                        if (resource.equalsIgnoreCase("CreateAccountController")) {
                            url = HOME_STUDENT;
                        } else if (resource.equalsIgnoreCase("CreateQuestionController")) {
                            url = HOME_STUDENT;
                        } else if (resource.equalsIgnoreCase("LoginController")) {
                            url = HOME_STUDENT;
                        } else if (resource.equalsIgnoreCase("RemoveQuestionController")) {
                            url = HOME_STUDENT;
                        } else if (resource.equalsIgnoreCase("UpdateQuestionController")) {
                            url = HOME_STUDENT;
                        } else if (resource.equalsIgnoreCase("ViewFormUpdateQuestionController")) {
                            url = HOME_STUDENT;
                        } else if (resource.equalsIgnoreCase("ViewQuestionByAdminController")) {
                            url = HOME_STUDENT;
                        }
                    }
                } else {
                    url = LOGIN;
                }
            } else if (user == null && url != null) {
                if (!resource.equalsIgnoreCase("LoginController")
                        && !resource.equalsIgnoreCase("formCreateAccount.jsp")
                        && !resource.equalsIgnoreCase("CreateAccountController")) {
                    url = LOGIN;
                }
            }
            if (url != null) {
                request.getRequestDispatcher(url).forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("MainController:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("MainController()");
        }
        StringBuffer sb = new StringBuffer("MainController(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);
        
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }
    
}
