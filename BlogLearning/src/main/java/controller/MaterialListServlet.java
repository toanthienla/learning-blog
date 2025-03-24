package controller;

import dao.MaterialsDAO;
import dao.ModulesDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Material;
import model.Module;
import model.Role;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "MaterialListServlet", urlPatterns = {"/materialList"})
public class MaterialListServlet extends HttpServlet {

    private final MaterialsDAO materialsDAO = new MaterialsDAO();
    private final ModulesDAO modulesDAO = new ModulesDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra quyền truy cập
        HttpSession session = request.getSession();
        Role userRole = (Role) session.getAttribute("role");
        if (userRole == null || userRole != Role.PUBLISHER) {
            response.sendRedirect("courses.jsp");
            return;
        }

        try {
            // Lấy moduleId từ request
            String moduleIdStr = request.getParameter("moduleId");
            if (moduleIdStr == null || moduleIdStr.isEmpty()) {
                throw new IllegalArgumentException("Module ID is missing.");
            }

            int moduleId = Integer.parseInt(moduleIdStr);

            // Lấy danh sách material theo moduleId
            List<Material> materials = materialsDAO.getMaterialsByModuleId(moduleId);
            request.setAttribute("materials", materials);
            request.setAttribute("moduleId", moduleId);

            // Lấy courseId của module
            Module module = modulesDAO.findById(moduleId);
            if (module == null) {
                throw new IllegalArgumentException("Module not found.");
            }
            int courseId = module.getCourseId();
            request.setAttribute("courseId", courseId);

            // Chuyển hướng đến materialList.jsp
            request.getRequestDispatcher("/WEB-INF/views/materialList.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            Logger.getLogger(MaterialListServlet.class.getName()).log(Level.SEVERE, "Invalid number format", e);
            session.setAttribute("errorMessage", "Invalid Module ID format: " + e.getMessage());
            response.sendRedirect("manageCourses");
        } catch (SQLException e) {
            Logger.getLogger(MaterialListServlet.class.getName()).log(Level.SEVERE, "Database error", e);
            session.setAttribute("errorMessage", "Database error: " + e.getMessage());
            response.sendRedirect("manageCourses");
        } catch (Exception e) {
            Logger.getLogger(MaterialListServlet.class.getName()).log(Level.SEVERE, "Unexpected error", e);
            session.setAttribute("errorMessage", "Unexpected error: " + e.getMessage());
            response.sendRedirect("manageCourses");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet for displaying the list of materials";
    }
}