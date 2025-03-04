/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CoursesDAO;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Course;
import model.Role;
import model.User;

/**
 *
 * @author Asus
 */
@WebServlet(name = "DashboardServlet", urlPatterns = {"/dashboard"})
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Check if user has logged in 
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        if (user == null) {
            response.sendRedirect("sign-in");
            return;
        }

        UserDAO userDao = new UserDAO();
        int rank = userDao.getUserRank(user.getId(), user.getRole());
        request.setAttribute("rank", rank);
        List<User> top3 = userDao.getTopThreeUser(user.getRole());
        request.setAttribute("top3", top3);

        CoursesDAO courseDao = new CoursesDAO();
        if (user.getRole().equals(Role.PUBLISHER)) {
            List<Course> ownCourses = courseDao.getOwnCourse(user.getId());
            request.setAttribute("ownCourses", ownCourses);
        }

        request.setAttribute("id", user.getId());
        request.setAttribute("role", user.getRole());
        request.setAttribute("username", user.getUsername());
        request.setAttribute("point", user.getPoint());

        request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
