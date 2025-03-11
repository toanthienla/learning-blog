/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CoursesDAO;
import model.Course;
import model.Role;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AdminServlet", urlPatterns = {"/admin"})
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Role role = (Role) session.getAttribute("role");

        // Log để kiểm tra role và session
        System.out.println("User Role: " + role);
        System.out.println("Session ID: " + session.getId());

        // Kiểm tra quyền truy cập, chỉ PUBLISHER được vào trang admin
        if (role == null || !Role.PUBLISHER.equals(role)) {
            System.out.println("Access denied: Redirecting to courses");
            response.sendRedirect("courses");
            return;
        }

        CoursesDAO coursesDAO = new CoursesDAO();
        try {
            List<Course> courses = coursesDAO.getAllCourses();
            request.setAttribute("courses", courses);
            request.getRequestDispatcher("WEB-INF/views/admin.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, "Database error", ex);
            request.setAttribute("errorMessage", "Error retrieving course data.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}