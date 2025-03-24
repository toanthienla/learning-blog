package controller;

import dao.ModulesDAO;
import model.Module;
import model.Role;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "EditModuleServlet", urlPatterns = {"/editModule"})
public class EditModuleServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(EditModuleServlet.class.getName());
    private final ModulesDAO modulesDAO = new ModulesDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Role userRole = (Role) session.getAttribute("role");
        String moduleIdStr = request.getParameter("moduleId");

        // Kiểm tra quyền truy cập
        if (userRole == null || userRole != Role.PUBLISHER) {
            String redirectModuleId = (moduleIdStr != null && !moduleIdStr.isEmpty()) ? moduleIdStr : "1";
            try {
                int moduleId = Integer.parseInt(redirectModuleId);
                Module module = modulesDAO.findById(moduleId);
                if (module != null) {
                    response.sendRedirect("moduleList?courseId=" + module.getCourseId());
                } else {
                    response.sendRedirect("moduleList?courseId=1"); // Giá trị mặc định nếu không tìm thấy module
                }
            } catch (NumberFormatException | SQLException e) {
                response.sendRedirect("moduleList?courseId=1");
            }
            return;
        }

        try {
            // Kiểm tra moduleId hợp lệ
            if (moduleIdStr == null || moduleIdStr.isEmpty()) {
                throw new IllegalArgumentException("Module ID is missing.");
            }

            int moduleId = Integer.parseInt(moduleIdStr);
            Module module = modulesDAO.findById(moduleId);
            if (module == null) {
                throw new IllegalArgumentException("Module with ID " + moduleId + " not found.");
            }

            // Gửi thông tin module tới editModule.jsp
            request.setAttribute("id", module.getModuleId());
            request.setAttribute("moduleName", module.getModuleName());
            request.setAttribute("courseId", module.getCourseId());
            request.setAttribute("lastUpdate", module.getLastUpdate());

            request.getRequestDispatcher("/WEB-INF/views/editModule.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid number format for moduleId: " + moduleIdStr, e);
            session.setAttribute("errorMessage", "Invalid Module ID format: " + e.getMessage());
            response.sendRedirect("moduleList?courseId=1");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error while fetching module with ID: " + moduleIdStr, e);
            session.setAttribute("errorMessage", "Database error: " + e.getMessage());
            response.sendRedirect("moduleList?courseId=1");
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Invalid request: " + e.getMessage());
            session.setAttribute("errorMessage", e.getMessage());
            response.sendRedirect("moduleList?courseId=1");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error in doGet for moduleId: " + moduleIdStr, e);
            session.setAttribute("errorMessage", "Unexpected error: " + e.getMessage());
            response.sendRedirect("moduleList?courseId=1");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Role userRole = (Role) session.getAttribute("role");
        String moduleIdStr = request.getParameter("id");

        // Kiểm tra quyền truy cập
        if (userRole == null || userRole != Role.PUBLISHER) {
            String redirectModuleId = (moduleIdStr != null && !moduleIdStr.isEmpty()) ? moduleIdStr : "1";
            try {
                int moduleId = Integer.parseInt(redirectModuleId);
                Module module = modulesDAO.findById(moduleId);
                if (module != null) {
                    response.sendRedirect("moduleList?courseId=" + module.getCourseId());
                } else {
                    response.sendRedirect("moduleList?courseId=1");
                }
            } catch (NumberFormatException | SQLException e) {
                response.sendRedirect("moduleList?courseId=1");
            }
            return;
        }

        try {
            // Lấy dữ liệu từ form
            String moduleName = request.getParameter("moduleName");
            String courseIdStr = request.getParameter("courseId");

            // Kiểm tra null hoặc rỗng
            if (moduleIdStr == null || moduleIdStr.isEmpty() ||
                moduleName == null || moduleName.isEmpty() ||
                courseIdStr == null || courseIdStr.isEmpty()) {
                throw new IllegalArgumentException("Missing required parameters.");
            }

            // Chuyển đổi dữ liệu
            int moduleId = Integer.parseInt(moduleIdStr);
            int courseId = Integer.parseInt(courseIdStr);

            // Kiểm tra module tồn tại
            Module existingModule = modulesDAO.findById(moduleId);
            if (existingModule == null) {
                throw new IllegalArgumentException("Module with ID " + moduleId + " not found.");
            }

            // Kiểm tra CourseId tồn tại
            if (!modulesDAO.checkCourseIdExists(courseId)) {
                throw new IllegalArgumentException("Course ID " + courseId + " does not exist.");
            }

            // Tạo đối tượng Module
            Date lastUpdate = new Date(); // Cập nhật LastUpdate với thời gian hiện tại
            Module module = new Module(moduleId, moduleName, lastUpdate, courseId);

            // Cập nhật module
            boolean updated = modulesDAO.updateModule(module);
            if (updated) {
                session.setAttribute("successMessage", "Module updated successfully!");
                response.sendRedirect("moduleList?courseId=" + courseId);
            } else {
                throw new SQLException("Failed to update module. It may not exist.");
            }

        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Invalid number format: id=" + moduleIdStr + ", courseId=" + request.getParameter("courseId"), e);
            setFormAttributes(request, moduleIdStr, request.getParameter("moduleName"), request.getParameter("courseId"), null);
            request.setAttribute("errorMessage", "Invalid number format: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/editModule.jsp").forward(request, response);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error while updating module with ID: " + moduleIdStr, e);
            setFormAttributes(request, moduleIdStr, request.getParameter("moduleName"), request.getParameter("courseId"), null);
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/editModule.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Invalid request: " + e.getMessage());
            setFormAttributes(request, moduleIdStr, request.getParameter("moduleName"), request.getParameter("courseId"), null);
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/editModule.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error in doPost for moduleId: " + moduleIdStr, e);
            setFormAttributes(request, moduleIdStr, request.getParameter("moduleName"), request.getParameter("courseId"), null);
            request.setAttribute("errorMessage", "Unexpected error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/editModule.jsp").forward(request, response);
        }
    }

    // Hàm hỗ trợ để đặt lại các giá trị form khi có lỗi
    private void setFormAttributes(HttpServletRequest request, String id, String moduleName, String courseId, java.sql.Date lastUpdate) {
        request.setAttribute("id", id);
        request.setAttribute("moduleName", moduleName);
        request.setAttribute("courseId", courseId);
        request.setAttribute("lastUpdate", lastUpdate);
    }
}