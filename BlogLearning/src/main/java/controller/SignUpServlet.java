/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.UserDAO;
import jakarta.mail.MessagingException;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import model.Role;
import model.User;

/**
 *
 * @author Asus
 */
@WebServlet(name = "SignUpServlet", urlPatterns = {"/sign-up"})
public class SignUpServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/signUp.jsp").forward(request, response);
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
        HttpSession session = request.getSession(); //Create session object
        try {
            //Get parameter from sign up form
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            Role role = request.getParameter("role").equalsIgnoreCase("publisher") ? Role.PUBLISHER : Role.READER;

            //Create DAO and create account using dao
            UserDAO dao = new UserDAO();
            User user = dao.createAccount(username, email, password, role); //This will throws Exceptions

            //Set account to new user created and sign up success to true
            //session.setAttribute("signUpSuccess", true);
            session.setAttribute("account", user);
            //request.getRequestDispatcher("main").forward(request, response);
            response.sendRedirect("dashboard");
        } catch (IllegalArgumentException e) {
            //request.setAttribute("errMsg", e.getMessage());
            //request.getRequestDispatcher("signUp.jsp").forward(request, response);
            session.setAttribute("errMsg", e.getMessage());
            response.sendRedirect("sign-up");
        } catch (SQLException e) {
            //request.setAttribute("errMsg", "Internal server error!");
            //request.getRequestDispatcher("signUp.jsp").forward(request, response);
            session.setAttribute("errMsg", "Internal server error!");
            response.sendRedirect("sign-up");
        } catch (MessagingException e) {
            //request.setAttribute("errMsg", "Invalid email address!");
            //request.getRequestDispatcher("signUp.jsp").forward(request, response);
            session.setAttribute("errMsg", "Invalid email address!");
            response.sendRedirect("sign-up");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
