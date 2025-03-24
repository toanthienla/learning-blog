package controller;

import dao.ModulesDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Module;
import model.Role;

@WebServlet(name = "ModuleListServlet", urlPatterns = {"/moduleList"})
public class ModuleListServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Kiểm tra quyền truy cập
        HttpSession session = request.getSession();
        Role userRole = (Role) session.getAttribute("role");
        if (userRole == null || userRole != Role.PUBLISHER) {
            response.sendRedirect("courses.jsp");
            return;
        }

        try {
            ModulesDAO modulesDAO = new ModulesDAO();
            String courseIdStr = request.getParameter("courseId");

            if (courseIdStr == null || courseIdStr.isEmpty()) {
                request.setAttribute("errorMessage", "Course ID is required.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }

            int courseId = Integer.parseInt(courseIdStr);
            List<Module> modules = modulesDAO.getModulesByCourseId(courseId);
            request.setAttribute("modules", modules);
            request.setAttribute("courseId", courseId);
            request.getRequestDispatcher("/WEB-INF/views/moduleList.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid Course ID format.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while retrieving modules: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for listing modules by course ID";
    }
}