/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package danhpv.listener;

import danhpv.daos.SubjectDAO;
import danhpv.dtos.Subject;
import java.io.File;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.PropertyConfigurator;

/**
 * Web application lifecycle listener.
 *
 * @author DELL
 */
public class FirstListenerController implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            ServletContext context = event.getServletContext();
            String log4jConfigFile = context.getInitParameter("log4j-config-location");
            String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;
            PropertyConfigurator.configure(fullPath);
            SubjectDAO subjectDao = new SubjectDAO();
            List<Subject> listSubjects = subjectDao.getAllSubject();
            context.setAttribute("LIST_SUBJECT", listSubjects);
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        context.removeAttribute("LIST_SUBJECT");
    }
}
