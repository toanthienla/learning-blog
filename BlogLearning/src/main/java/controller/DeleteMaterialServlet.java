package controller;

import dao.MaterialsDAO;
import dao.ModulesDAO;
import model.Material;
import model.Module;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "DeleteMaterialServlet", urlPatterns = {"/deleteMaterial"})
public class DeleteMaterialServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            if (idStr == null || idStr.isEmpty()) {
                request.setAttribute("errorMessage", "Material ID is missing.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }

            int id = Integer.parseInt(idStr);
            MaterialsDAO dao = new MaterialsDAO();
            Material material = dao.findById(id);
            if (material == null) {
                request.setAttribute("errorMessage", "Material not found.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                return;
            }

            // Lấy moduleId để gửi đến confirmDeleteMaterial.jsp
            int moduleId = material.getModuleId();

            // Gửi materialId và moduleId tới confirmDeleteMaterial.jsp
            request.setAttribute("materialId", idStr);
            request.setAttribute("moduleId", moduleId);
            request.getRequestDispatcher("/WEB-INF/views/confirmDeleteMaterial.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            Logger.getLogger(DeleteMaterialServlet.class.getName()).log(Level.SEVERE, "Invalid number format", e);
            request.setAttribute("errorMessage", "Invalid number format: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        } catch (SQLException e) {
            Logger.getLogger(DeleteMaterialServlet.class.getName()).log(Level.SEVERE, "Database error", e);
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            String idStr = request.getParameter("id");
            if (idStr == null || idStr.isEmpty()) {
                throw new IllegalArgumentException("Material ID is missing.");
            }

            int id = Integer.parseInt(idStr);
            MaterialsDAO dao = new MaterialsDAO();
            Material material = dao.findById(id);
            if (material == null) {
                throw new IllegalArgumentException("Material not found.");
            }

            // Lấy moduleId từ material
            int moduleId = material.getModuleId();

            // Xóa material
            boolean deleted = dao.deleteMaterial(id);

            if (deleted) {
                // Đặt thông báo thành công
                session.setAttribute("successMessage", "Material deleted successfully!");
                // Chuyển hướng về materialList với moduleId
                response.sendRedirect("materialList?moduleId=" + moduleId);
            } else {
                // Nếu xóa thất bại
                request.setAttribute("errorMessage", "Failed to delete material. It may not exist.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            Logger.getLogger(DeleteMaterialServlet.class.getName()).log(Level.SEVERE, "Invalid number format", e);
            request.setAttribute("errorMessage", "Invalid number format: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        } catch (SQLException e) {
            Logger.getLogger(DeleteMaterialServlet.class.getName()).log(Level.SEVERE, "Database error", e);
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        } catch (Exception e) {
            Logger.getLogger(DeleteMaterialServlet.class.getName()).log(Level.SEVERE, "Unexpected error", e);
            request.setAttribute("errorMessage", "Unexpected error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}