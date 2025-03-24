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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "DeleteModuleServlet", urlPatterns = {"/deleteModule"})
public class DeleteModuleServlet extends HttpServlet {

    private final ModulesDAO modulesDAO = new ModulesDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Kiểm tra quyền truy cập
        HttpSession session = request.getSession();
        Role userRole = (Role) session.getAttribute("role");
        if (userRole == null || userRole != Role.PUBLISHER) {
            response.sendRedirect("courses.jsp");
            return;
        }

        try {
            // Kiểm tra tham số moduleId
            String moduleIdStr = request.getParameter("moduleId");
            if (moduleIdStr == null || moduleIdStr.isEmpty()) {
                throw new IllegalArgumentException("Module ID is missing.");
            }

            // Kiểm tra tham số courseId
            String courseIdStr = request.getParameter("courseId");
            if (courseIdStr == null || courseIdStr.isEmpty()) {
                throw new IllegalArgumentException("Course ID is missing.");
            }

            int moduleId = Integer.parseInt(moduleIdStr);
            int courseId = Integer.parseInt(courseIdStr);

            // Xóa module
            boolean deleted = modulesDAO.deleteModule(moduleId);

            if (deleted) {
                session.setAttribute("successMessage", "Module deleted successfully!");
            } else {
                session.setAttribute("errorMessage", "Failed to delete module. It may not exist.");
            }

            // Chuyển hướng về moduleList
            response.sendRedirect("moduleList?courseId=" + courseId);

        } catch (NumberFormatException e) {
            Logger.getLogger(DeleteModuleServlet.class.getName()).log(Level.SEVERE, "Invalid number format", e);
            handleError(request, response, "Invalid Module ID or Course ID format: " + e.getMessage());
        } catch (SQLException e) {
            Logger.getLogger(DeleteModuleServlet.class.getName()).log(Level.SEVERE, "Database error", e);
            handleError(request, response, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Logger.getLogger(DeleteModuleServlet.class.getName()).log(Level.SEVERE, "Unexpected error", e);
            handleError(request, response, "Unexpected error: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Chuyển hướng về moduleList nếu ai đó cố gắng truy cập qua GET
        String courseId = request.getParameter("courseId");
        if (courseId != null && !courseId.isEmpty()) {
            response.sendRedirect("moduleList?courseId=" + courseId);
        } else {
            response.sendRedirect("manageCourses");
        }
    }

    // Phương thức xử lý lỗi: tải lại danh sách module trước khi forward
    private void handleError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("errorMessage", errorMessage);

        try {
            String courseIdStr = request.getParameter("courseId");
            if (courseIdStr == null || courseIdStr.isEmpty()) {
                response.sendRedirect("manageCourses");
                return;
            }

            int courseId = Integer.parseInt(courseIdStr);
            List<Module> modules = modulesDAO.getModulesByCourseId(courseId);
            request.setAttribute("modules", modules);
            request.setAttribute("courseId", courseId);
            request.getRequestDispatcher("/WEB-INF/views/moduleList.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            Logger.getLogger(DeleteModuleServlet.class.getName()).log(Level.SEVERE, "Invalid course ID format", e);
            session.setAttribute("errorMessage", "Invalid Course ID format: " + e.getMessage());
            response.sendRedirect("manageCourses");
        } catch (SQLException e) {
            Logger.getLogger(DeleteModuleServlet.class.getName()).log(Level.SEVERE, "Database error while reloading modules", e);
            session.setAttribute("errorMessage", "Database error while reloading modules: " + e.getMessage());
            response.sendRedirect("manageCourses");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for deleting a module";
    }
}