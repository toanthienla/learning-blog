package controller;

import dao.MaterialsDAO;
import model.Material;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

/**
 * @author thuan
 */
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1MB
    maxFileSize = 5 * 1024 * 1024,   // 5MB
    maxRequestSize = 20 * 1024 * 1024 // 20MB
)
@WebServlet(name = "AddMaterialServlet", urlPatterns = {"/addMaterial"})
public class AddMaterialServlet extends HttpServlet {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy moduleId từ query parameter (nếu có) để truyền vào addMaterial.jsp
        String moduleIdStr = request.getParameter("moduleId");
        if (moduleIdStr != null && !moduleIdStr.isEmpty()) {
            request.setAttribute("moduleId", moduleIdStr);
        }
        // Chuyển tiếp đến trang addMaterial.jsp để hiển thị form
        request.getRequestDispatcher("/WEB-INF/views/addMaterial.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            // Lấy dữ liệu từ form
            String materialIdStr = request.getParameter("materialId");
            String materialName = request.getParameter("materialName");
            String materialType = request.getParameter("materialType");
            String lastUpdateStr = request.getParameter("lastUpdate");
            String moduleIdStr = request.getParameter("moduleId");

            // Xử lý upload file
            Part filePart = request.getPart("file"); // "file" là tên trường trong form
            if (filePart == null || filePart.getSize() == 0) {
                throw new IllegalArgumentException("No file uploaded.");
            }
            String fileName = filePart.getSubmittedFileName();

            // Định nghĩa thư mục lưu trữ
            String projectRoot = System.getProperty("user.dir");
            File storageDir = new File(projectRoot, "materials");
            if (!storageDir.exists()) {
                storageDir.mkdirs();
            }

            // Lưu file
            File file = new File(storageDir, fileName);
            try (InputStream fileContent = filePart.getInputStream()) {
                Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            // Tạo đường dẫn cho trường location
            String location = "materials/" + fileName;

            // Kiểm tra null hoặc rỗng cho các trường khác
            if (materialIdStr == null || materialIdStr.isEmpty() ||
                materialName == null || materialName.isEmpty() ||
                materialType == null || materialType.isEmpty() ||
                moduleIdStr == null || moduleIdStr.isEmpty()) {
                throw new IllegalArgumentException("Missing required parameters.");
            }

            // Chuyển đổi kiểu dữ liệu
            int materialId = Integer.parseInt(materialIdStr);
            int moduleId = Integer.parseInt(moduleIdStr);
            Date lastUpdate = lastUpdateStr != null && !lastUpdateStr.isEmpty() 
                    ? dateFormat.parse(lastUpdateStr) 
                    : new Date(); // Nếu không cung cấp, dùng ngày hiện tại

            // Tạo đối tượng Material và thêm vào database
            Material material = new Material(location, materialId, materialName, materialType, new java.sql.Date(lastUpdate.getTime()), moduleId);
            MaterialsDAO dao = new MaterialsDAO();
            boolean added = dao.addMaterial(material);

            if (added) {
                // Đặt thông báo thành công
                session.setAttribute("successMessage", "Material added successfully!");
                // Chuyển hướng về materialList với moduleId
                response.sendRedirect("materialList?moduleId=" + moduleId);
            } else {
                request.setAttribute("errorMessage", "Failed to add material.");
                request.setAttribute("materialId", materialIdStr);
                request.setAttribute("materialName", materialName);
                request.setAttribute("materialType", materialType);
                request.setAttribute("lastUpdate", lastUpdateStr);
                request.setAttribute("moduleId", moduleIdStr);
                request.getRequestDispatcher("/WEB-INF/views/addMaterial.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            Logger.getLogger(AddMaterialServlet.class.getName()).log(Level.SEVERE, "Invalid number format", e);
            request.setAttribute("errorMessage", "Invalid number format: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/addMaterial.jsp").forward(request, response);
        } catch (ParseException e) {
            Logger.getLogger(AddMaterialServlet.class.getName()).log(Level.SEVERE, "Invalid date format", e);
            request.setAttribute("errorMessage", "Invalid date format: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/addMaterial.jsp").forward(request, response);
        } catch (SQLException e) {
            Logger.getLogger(AddMaterialServlet.class.getName()).log(Level.SEVERE, "Database error", e);
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/addMaterial.jsp").forward(request, response);
        } catch (Exception e) {
            Logger.getLogger(AddMaterialServlet.class.getName()).log(Level.SEVERE, "Unexpected error", e);
            request.setAttribute("errorMessage", "Unexpected error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/addMaterial.jsp").forward(request, response);
        }
    }
}