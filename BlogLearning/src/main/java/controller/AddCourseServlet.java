package controller;

import dao.CoursesDAO;
import model.Course;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AddCourseServlet", urlPatterns = {"/addCourse"})
public class AddCourseServlet extends HttpServlet {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy dữ liệu từ form
            String courseIdStr = request.getParameter("courseId");
            String courseName = request.getParameter("courseName");
            String publicDateStr = request.getParameter("publicDate");
            String authorIdStr = request.getParameter("authorId");

            // Kiểm tra null hoặc rỗng
            if (courseIdStr == null || courseIdStr.isEmpty() ||
                courseName == null || courseName.isEmpty() ||
                publicDateStr == null || publicDateStr.isEmpty() ||
                authorIdStr == null || authorIdStr.isEmpty()) {
                throw new IllegalArgumentException("Missing required parameters.");
            }

            // Chuyển đổi kiểu dữ liệu
            int courseId = Integer.parseInt(courseIdStr);
            int authorId = Integer.parseInt(authorIdStr);
            Date publicDate = dateFormat.parse(publicDateStr);

            // Gán lastUpdate là ngày hiện tại
            Date lastUpdate = new Date();

            // Tạo đối tượng Course và thêm vào database
            Course course = new Course(courseId, courseName, publicDate, lastUpdate, authorId);
            new CoursesDAO().addCourse(course);

            // Chuyển hướng đến trang admin sau khi thêm thành công
            response.sendRedirect("admin");

        } catch (NumberFormatException e) {
            Logger.getLogger(AddCourseServlet.class.getName()).log(Level.SEVERE, "Invalid number format", e);
            request.setAttribute("errorMessage", "Invalid number format: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/addCourse.jsp").forward(request, response);
        } catch (ParseException e) {
            Logger.getLogger(AddCourseServlet.class.getName()).log(Level.SEVERE, "Invalid date format", e);
            request.setAttribute("errorMessage", "Invalid date format: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/addCourse.jsp").forward(request, response);
        } catch (Exception e) {
            Logger.getLogger(AddCourseServlet.class.getName()).log(Level.SEVERE, "Unexpected error", e);
            request.setAttribute("errorMessage", "Unexpected error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/addCourse.jsp").forward(request, response);
        }
    }
}
