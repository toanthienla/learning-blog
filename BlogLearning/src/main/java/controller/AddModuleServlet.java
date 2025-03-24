package controller;

import dao.ModulesDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Module;
import model.Role;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "AddModuleServlet", urlPatterns = {"/addModule"})
public class AddModuleServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AddModuleServlet.class.getName());
    private final ModulesDAO modulesDAO = new ModulesDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Role userRole = (Role) session.getAttribute("role");
        String courseIdStr = request.getParameter("courseId");

        // Kiểm tra quyền truy cập
        if (userRole == null || userRole != Role.PUBLISHER) {
            String redirectCourseId = (courseIdStr != null && !courseIdStr.isEmpty()) ? courseIdStr : "1";
            response.sendRedirect("moduleList?courseId=" + redirectCourseId);
            return;
        }

        try {
            // Kiểm tra courseId hợp lệ
            if (courseIdStr == null || courseIdStr.isEmpty()) {
                throw new IllegalArgumentException("Course ID is missing.");
            }

            int courseId = Integer.parseInt(courseIdStr);
            // Kiểm tra xem CourseId có tồn tại không
            if (!modulesDAO.checkCourseIdExists(courseId)) {
                throw new IllegalArgumentException("Course ID " + courseId + " does not exist.");
            }

            request.setAttribute("courseId", courseId);
            request.getRequestDispatcher("/WEB-INF/views/addModule.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid number format for courseId: " + courseIdStr, e);
            session.setAttribute("errorMessage", "Invalid Course ID format: " + e.getMessage());
            response.sendRedirect("moduleList?courseId=1");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error while checking courseId: " + courseIdStr, e);
            session.setAttribute("errorMessage", "Database error: " + e.getMessage());
            response.sendRedirect("moduleList?courseId=1");
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Invalid request: " + e.getMessage());
            session.setAttribute("errorMessage", e.getMessage());
            response.sendRedirect("moduleList?courseId=1");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error in doGet for courseId: " + courseIdStr, e);
            session.setAttribute("errorMessage", "Unexpected error: " + e.getMessage());
            response.sendRedirect("moduleList?courseId=1");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Role userRole = (Role) session.getAttribute("role");
        String moduleName = request.getParameter("moduleName");
        String courseIdStr = request.getParameter("courseId");
        String moduleIdStr = request.getParameter("moduleId");

        // Kiểm tra quyền truy cập
        if (userRole == null || userRole != Role.PUBLISHER) {
            String redirectCourseId = (courseIdStr != null && !courseIdStr.isEmpty()) ? courseIdStr : "1";
            response.sendRedirect("moduleList?courseId=" + redirectCourseId);
            return;
        }

        try {
            // Kiểm tra null hoặc rỗng
            if (moduleName == null || moduleName.isEmpty() || 
                courseIdStr == null || courseIdStr.isEmpty() || 
                moduleIdStr == null || moduleIdStr.isEmpty()) {
                throw new IllegalArgumentException("Module name, Course ID, and Module ID are required.");
            }

            int courseId = Integer.parseInt(courseIdStr);
            int moduleId = Integer.parseInt(moduleIdStr);

            // Kiểm tra xem moduleId có phải là số dương không
            if (moduleId <= 0) {
                throw new IllegalArgumentException("Module ID must be a positive number.");
            }

            // Kiểm tra xem CourseId có tồn tại không
            if (!modulesDAO.checkCourseIdExists(courseId)) {
                throw new IllegalArgumentException("Course ID " + courseId + " does not exist.");
            }

            // Kiểm tra xem moduleId đã tồn tại chưa
            Module existingModule = modulesDAO.findById(moduleId);
            if (existingModule != null) {
                throw new IllegalArgumentException("Module ID " + moduleId + " already exists.");
            }

            // Tạo module mới và thêm vào database
            Module newModule = new Module(moduleId, moduleName, courseId);
            boolean added = modulesDAO.addModule(newModule);

            if (added) {
                session.setAttribute("successMessage", "Module added successfully!");
                response.sendRedirect("moduleList?courseId=" + courseId);
            } else {
                throw new SQLException("Failed to add module.");
            }

        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid number format: moduleId=" + moduleIdStr + ", courseId=" + courseIdStr, e);
            setFormAttributes(request, moduleIdStr, moduleName, courseIdStr);
            request.setAttribute("errorMessage", "Invalid number format: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/addModule.jsp").forward(request, response);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error while adding module with ID: " + moduleIdStr, e);
            setFormAttributes(request, moduleIdStr, moduleName, courseIdStr);
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/addModule.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Invalid request: " + e.getMessage());
            setFormAttributes(request, moduleIdStr, moduleName, courseIdStr);
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/addModule.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error in doPost for moduleId: " + moduleIdStr, e);
            setFormAttributes(request, moduleIdStr, moduleName, courseIdStr);
            request.setAttribute("errorMessage", "Unexpected error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/addModule.jsp").forward(request, response);
        }
    }

    // Hàm hỗ trợ để đặt lại các giá trị form khi có lỗi
    private void setFormAttributes(HttpServletRequest request, String moduleId, String moduleName, String courseId) {
        request.setAttribute("moduleId", moduleId);
        request.setAttribute("moduleName", moduleName);
        request.setAttribute("courseId", courseId);
    }
}