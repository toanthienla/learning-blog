/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CoursesDAO;
import dao.EnrollDAO;
import dao.MaterialsDAO;
import dao.ModulesDAO;
import dao.StudyDAO;
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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        if (user == null) {
            response.sendRedirect("sign-in");
            return;
        }

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
            int materialId;
            if (materialIdString == null || materialIdString.isEmpty()) {
                // Default select top1 materials
                material = materialsDAO.getTopMaterialsByModuleId(moduleId);
                materialId = material.getMaterialId();
            } else {
                // Get material by params
                materialId = Integer.parseInt(materialIdString);
                material = materialsDAO.findById(materialId);
            }

            // Check user already study this material
            StudyDAO studyDAO = new StudyDAO();
            boolean isMaterialStudiedByUser = studyDAO.isMaterialStudiedByUser(user.getId(), materialId);
            if (isMaterialStudiedByUser) {
                request.setAttribute("isUserStudied", true);
            } else {
                request.setAttribute("isUserStudied", false);
            }

            request.setAttribute("materials", materials);
            request.setAttribute("courseName", course.getCourseName());
            request.setAttribute("courseId", course.getCourseId());
            request.setAttribute("moduleName", module.getModuleName());
            request.setAttribute("moduleId", module.getModuleId());
            request.setAttribute("material", material);
            request.setAttribute("materialId", material.getMaterialId());

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
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        String courseIdString = request.getParameter("courseId");
        String materialIdString = request.getParameter("materialId");

        if (courseIdString == null || courseIdString.isEmpty() || materialIdString == null || materialIdString.isEmpty()) {
            response.getWriter().println("Course ID or Material ID is missing.");
            return;
        }
        int userId = user.getId();
        int courseId = Integer.parseInt(courseIdString);
        int materiaId = Integer.parseInt(materialIdString);

        try {
            StudyDAO studyDAO = new StudyDAO();
            System.out.println("Action type: " + action);
            if ("mark".equals(action)) {

                // Insert to user with courseId to Enroll table
                EnrollDAO enrollDAO = new EnrollDAO();
                if (!enrollDAO.isUserEnrollCourse(userId, courseId)) {
                    enrollDAO.insertUser(userId, courseId);
                }

                // Mark material studied
                studyDAO.markMaterialAsStudied(userId, materiaId);
            } else if ("unmark".equals(action)) {
                System.out.println("Unmark user materials");
                // Unmark material studied
                studyDAO.unmarkMaterialAsStudied(userId, materiaId);
            }

            // Invert the isMaterialStudiedByUser value after marking/unmarking
            boolean isMaterialStudiedByUser = !studyDAO.isMaterialStudiedByUser(userId, materiaId);
            request.setAttribute("isUserStudied", isMaterialStudiedByUser);

            // Forward to the same page (doGet) to reflect the updated status
            doGet(request, response);
        } catch (NumberFormatException | SQLException e) {
            System.out.println(e);
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
    }// </editor-fold>

}
