/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CoursesDAO;
import dao.MaterialsDAO;
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
import model.Course;
import model.Material;
import model.Module;
import model.User;

/**
 *
 * @author XPS
 */
@WebServlet(name = "MaterialsServlet", urlPatterns = {"/materials"})
public class MaterialsServlet extends HttpServlet {

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
            out.println("<title>Servlet MaterialsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MaterialsServlet at " + request.getContextPath() + "</h1>");
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

        // Get courseId and moduleId params
        String courseIdString = request.getParameter("courseId");
        String moduleIdString = request.getParameter("moduleId");
        String materialIdString = request.getParameter("materialId");
        if (courseIdString == null || courseIdString.isEmpty() || moduleIdString == null || moduleIdString.isEmpty()) {
            response.getWriter().println("Course ID is missing.");
            return;
        }

        try {
            int courseId = Integer.parseInt(courseIdString);
            int moduleId = Integer.parseInt(moduleIdString);

            MaterialsDAO materialsDAO = new MaterialsDAO();
            ModulesDAO modulesDAO = new ModulesDAO();
            CoursesDAO coursesDAO = new CoursesDAO();

            List<Material> materials = materialsDAO.getMaterialsByModuleId(moduleId);
            Course course = coursesDAO.findById(courseId);
            Module module = modulesDAO.findById(moduleId);

            Material material;
            if (materialIdString == null || materialIdString.isEmpty()) {
                material = materialsDAO.getTopMaterialsByModuleId(moduleId);
            } else {
                int materialId = Integer.parseInt(materialIdString);
                material = materialsDAO.findById(materialId);
            }

            request.setAttribute("materials", materials);
            request.setAttribute("courseName", course.getCourseName());
            request.setAttribute("courseId", course.getCourseId());
            request.setAttribute("moduleName", module.getModuleName());
            request.setAttribute("moduleId", module.getModuleId());
            request.setAttribute("material", material);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/materials.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid courseId or moduleId (NumberFormatException).");
        } catch (SQLException ex) {
            response.getWriter().println("Invalid courseId or moduleId (SQLException)");
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
