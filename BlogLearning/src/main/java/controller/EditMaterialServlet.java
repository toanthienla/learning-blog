package controller;

import dao.MaterialsDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.Material;
import model.Role;

import java.io.File;
import java.io.IOException;
import java.sql.Date; // Thêm import này
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "EditMaterialServlet", urlPatterns = {"/editMaterial"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class EditMaterialServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "uploads";
    private final MaterialsDAO materialsDAO = new MaterialsDAO();

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
            // Lấy id từ request
            String idStr = request.getParameter("id");
            if (idStr == null || idStr.isEmpty()) {
                throw new IllegalArgumentException("Material ID is missing.");
            }

            int id = Integer.parseInt(idStr);

            // Lấy material từ database
            Material material = materialsDAO.findById(id);
            if (material == null) {
                throw new IllegalArgumentException("Material not found.");
            }

            // Đặt các thuộc tính vào request để hiển thị trên form
            request.setAttribute("id", material.getMaterialId());
            request.setAttribute("materialName", material.getMaterialName());
            request.setAttribute("location", material.getLocation());
            request.setAttribute("materialType", material.getMaterialType());
            // Chuyển java.sql.Date thành String định dạng yyyy-MM-dd
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            request.setAttribute("lastUpdate", dateFormat.format(material.getLastUpdate()));
            request.setAttribute("moduleId", material.getModuleId());

            // Chuyển hướng đến editMaterial.jsp
            request.getRequestDispatcher("/WEB-INF/views/editMaterial.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            Logger.getLogger(EditMaterialServlet.class.getName()).log(Level.SEVERE, "Invalid number format", e);
            session.setAttribute("errorMessage", "Invalid Material ID format: " + e.getMessage());
            response.sendRedirect("manageCourses");
        } catch (SQLException e) {
            Logger.getLogger(EditMaterialServlet.class.getName()).log(Level.SEVERE, "Database error", e);
            session.setAttribute("errorMessage", "Database error: " + e.getMessage());
            response.sendRedirect("manageCourses");
        } catch (Exception e) {
            Logger.getLogger(EditMaterialServlet.class.getName()).log(Level.SEVERE, "Unexpected error", e);
            session.setAttribute("errorMessage", "Unexpected error: " + e.getMessage());
            response.sendRedirect("manageCourses");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra quyền truy cập
        HttpSession session = request.getSession();
        Role userRole = (Role) session.getAttribute("role");
        if (userRole == null || userRole != Role.PUBLISHER) {
            response.sendRedirect("courses.jsp");
            return;
        }

        try {
            // Lấy dữ liệu từ form
            String idStr = request.getParameter("id");
            String materialName = request.getParameter("materialName");
            String materialType = request.getParameter("materialType");
            String lastUpdateStr = request.getParameter("lastUpdate");
            String moduleIdStr = request.getParameter("moduleId");

            // Kiểm tra dữ liệu
            if (idStr == null || idStr.isEmpty() || materialName == null || materialName.isEmpty() ||
                    materialType == null || materialType.isEmpty() || moduleIdStr == null || moduleIdStr.isEmpty()) {
                throw new IllegalArgumentException("Required fields are missing.");
            }

            int id = Integer.parseInt(idStr);
            int moduleId = Integer.parseInt(moduleIdStr);

            // Kiểm tra moduleId có tồn tại không
            if (!materialsDAO.checkModuleIdExists(moduleId)) {
                request.setAttribute("errorMessage", "Module ID does not exist.");
                request.setAttribute("id", id);
                request.setAttribute("materialName", materialName);
                request.setAttribute("materialType", materialType);
                request.setAttribute("lastUpdate", lastUpdateStr);
                request.setAttribute("moduleId", moduleId);
                request.getRequestDispatcher("/WEB-INF/views/editMaterial.jsp").forward(request, response);
                return;
            }

            // Parse lastUpdate từ String thành java.util.Date
            java.util.Date lastUpdateUtil = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (lastUpdateStr != null && !lastUpdateStr.isEmpty()) {
                lastUpdateUtil = dateFormat.parse(lastUpdateStr);
            } else {
                lastUpdateUtil = new java.util.Date(); // Nếu không có ngày, sử dụng ngày hiện tại
            }

            // Chuyển java.util.Date thành java.sql.Date
            Date lastUpdate = new Date(lastUpdateUtil.getTime());

            // Xử lý file upload (nếu có)
            Part filePart = request.getPart("file");
            String location = null;
            if (filePart != null && filePart.getSize() > 0) {
                // Lấy tên file
                String fileName = extractFileName(filePart);
                // Đường dẫn lưu file
                String applicationPath = request.getServletContext().getRealPath("");
                String uploadPath = applicationPath + File.separator + UPLOAD_DIR;
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                // Lưu file
                String filePath = uploadPath + File.separator + fileName;
                filePart.write(filePath);
                location = UPLOAD_DIR + "/" + fileName;
            }

            // Lấy material hiện tại để giữ location nếu không upload file mới
            Material existingMaterial = materialsDAO.findById(id);
            if (existingMaterial == null) {
                throw new IllegalArgumentException("Material not found.");
            }
            if (location == null) {
                location = existingMaterial.getLocation(); // Giữ location cũ nếu không upload file mới
            }

            // Tạo đối tượng Material và cập nhật
            Material material = new Material();
            material.setMaterialId(id);
            material.setMaterialName(materialName);
            material.setLocation(location);
            material.setMaterialType(materialType);
            material.setLastUpdate(lastUpdate); // Gán java.sql.Date
            material.setModuleId(moduleId);

            // Cập nhật material trong database
            boolean updated = materialsDAO.updateMaterial(material);

            if (updated) {
                session.setAttribute("successMessage", "Material updated successfully!");
                // Chuyển hướng về materialList với moduleId
                response.sendRedirect("materialList?moduleId=" + moduleId);
            } else {
                request.setAttribute("errorMessage", "Failed to update material. It may not exist.");
                request.setAttribute("id", id);
                request.setAttribute("materialName", materialName);
                request.setAttribute("location", location);
                request.setAttribute("materialType", materialType);
                request.setAttribute("lastUpdate", lastUpdateStr);
                request.setAttribute("moduleId", moduleId);
                request.getRequestDispatcher("/WEB-INF/views/editMaterial.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            Logger.getLogger(EditMaterialServlet.class.getName()).log(Level.SEVERE, "Invalid number format", e);
            request.setAttribute("errorMessage", "Invalid number format: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/editMaterial.jsp").forward(request, response);
        } catch (SQLException e) {
            Logger.getLogger(EditMaterialServlet.class.getName()).log(Level.SEVERE, "Database error", e);
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/editMaterial.jsp").forward(request, response);
        } catch (Exception e) {
            Logger.getLogger(EditMaterialServlet.class.getName()).log(Level.SEVERE, "Unexpected error", e);
            request.setAttribute("errorMessage", "Unexpected error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/editMaterial.jsp").forward(request, response);
        }
    }

    // Hàm hỗ trợ để lấy tên file từ Part
    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] items = contentDisposition.split(";");
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                return item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }
        return "";
    }

    @Override
    public String getServletInfo() {
        return "Servlet for editing a material";
    }
}