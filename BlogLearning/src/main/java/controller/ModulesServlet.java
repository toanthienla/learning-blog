/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CoursesDAO;
import dao.ModulesDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Course;
import model.Module;
import model.User;

/**
 *
 * @author XPS
 */
@WebServlet(name = "ModulesServlet", urlPatterns = {"/modules"})
public class ModulesServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ModulesServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ModulesServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        // Get User information and courseId
//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("account");
//        if (user == null) {
//            response.sendRedirect("sign-in");
//            return;
//        }

        // Get courseId params
        String courseIdString = request.getParameter("courseId");
        if (courseIdString == null || courseIdString.isEmpty()) {
            response.getWriter().println("Course ID is missing.");
            return;
        }

        try {
            int courseId = Integer.parseInt(courseIdString);

            ModulesDAO modulesDAO = new ModulesDAO();
            CoursesDAO coursesDAO = new CoursesDAO();
            List<Module> modules = modulesDAO.getModulesByCourseId(courseId);
            Course course = coursesDAO.findById(courseId);
            
            request.setAttribute("modules", modules);
            request.setAttribute("courseName", course.getCourseName());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/modules.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid Course ID.");
        } catch (SQLException ex) {
            response.getWriter().println("Invalid Course ID.");
        }
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
