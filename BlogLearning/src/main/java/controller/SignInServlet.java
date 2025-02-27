/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import model.User;

/**
 *
 * @author Asus
 */
@WebServlet(name="SignInServlet", urlPatterns={"/sign-in"})
public class SignInServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/signIn.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Get session from request
        HttpSession session = request.getSession();
        try {
            //Get request parameter
            String email = request.getParameter("username");
            String password = request.getParameter("password");
            
            //Login
            UserDAO dao = new UserDAO();
            User user = dao.login(email, password);
            
            //Add attribute to session
            session.setAttribute("account", user);
            //request.getRequestDispatcher("main").forward(request, response);
            response.sendRedirect("dashboard");
        } catch (SQLException e) {
            session.setAttribute("errMsg", "Internal server error!");
            response.sendRedirect("sign-in");
        } catch (IllegalArgumentException e) {
            session.setAttribute("errMsg", e.getMessage());
            response.sendRedirect("sign-in");
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
