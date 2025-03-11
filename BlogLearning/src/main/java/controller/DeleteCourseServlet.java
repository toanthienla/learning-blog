/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CoursesDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteCourseServlet", urlPatterns = {"/deleteCourse"})
public class DeleteCourseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy ID từ request
            int id = Integer.parseInt(request.getParameter("id"));

            // Xóa khóa học theo ID
            new CoursesDAO().deleteCourse(id);

            // Chuyển hướng về trang admin
            response.sendRedirect("admin");
        } catch (NumberFormatException e) {
            Logger.getLogger(DeleteCourseServlet.class.getName()).log(Level.SEVERE, "Invalid course ID", e);
            response.sendRedirect("error.jsp");
        } catch (SQLException ex) {
            Logger.getLogger(DeleteCourseServlet.class.getName()).log(Level.SEVERE, "Database error", ex);
            response.sendRedirect("error.jsp");
        }
    }
}
