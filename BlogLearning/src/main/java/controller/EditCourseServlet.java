package controller;

import dao.CoursesDAO;
import model.Course;

import java.io.IOException;
import java.sql.SQLException;
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

@WebServlet(name = "EditCourseServlet", urlPatterns = {"/editCourse"})
public class EditCourseServlet extends HttpServlet {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");

            if ("add".equalsIgnoreCase(action)) {
                // Chuyển hướng tới addCourse.jsp
                request.getRequestDispatcher("/WEB-INF/views/addCourse.jsp").forward(request, response);
                return;
            }

            // Xử lý edit
            String idStr = request.getParameter("id");
            if (idStr == null || idStr.isEmpty()) {
                throw new IllegalArgumentException("Course ID is missing.");
            }

            int id = Integer.parseInt(idStr);

            CoursesDAO dao = new CoursesDAO();
            Course course = dao.findById(id);
            if (course == null) {
                throw new IllegalArgumentException("Course not found.");
            }

            // Gửi thông tin khóa học tới editCourse.jsp
            request.setAttribute("id", course.getCourseId());
            request.setAttribute("courseName", course.getCourseName());
            request.setAttribute("publicDate", dateFormat.format(course.getPublicDate()));
            request.setAttribute("lastUpdate", dateFormat.format(course.getLastUpdate()));
            request.setAttribute("authorId", course.getAuthorId());

            request.getRequestDispatcher("/WEB-INF/views/editCourse.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy dữ liệu từ form (bỏ lastUpdate)
            String idStr = request.getParameter("id");
            String courseName = request.getParameter("courseName");
            String publicDateStr = request.getParameter("publicDate");
            String authorIdStr = request.getParameter("authorId");

            // Kiểm tra null hoặc rỗng
            if (idStr == null || idStr.isEmpty()
                    || courseName == null || courseName.isEmpty()
                    || publicDateStr == null || publicDateStr.isEmpty()
                    || authorIdStr == null || authorIdStr.isEmpty()) {
                throw new IllegalArgumentException("Missing required parameters.");
            }

            // Chuyển đổi dữ liệu
            int id = Integer.parseInt(idStr);
            int authorId = Integer.parseInt(authorIdStr);
            Date publicDate = dateFormat.parse(publicDateStr);

            // Tạo giá trị lastUpdate là ngày hiện tại
            Date lastUpdate = new Date();

            // Tạo đối tượng Course
            Course course = new Course(id, courseName, publicDate, lastUpdate, authorId);
            new CoursesDAO().updateCourse(course);

            // Chuyển hướng tới trang admin sau khi cập nhật thành công
            response.sendRedirect("admin");

        } catch (NumberFormatException e) {
            Logger.getLogger(EditCourseServlet.class.getName()).log(Level.SEVERE, "Invalid number format", e);
            request.setAttribute("errorMessage", "Invalid number format: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/editCourse.jsp").forward(request, response);
        } catch (ParseException e) {
            Logger.getLogger(EditCourseServlet.class.getName()).log(Level.SEVERE, "Invalid date format", e);
            request.setAttribute("errorMessage", "Invalid date format: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/editCourse.jsp").forward(request, response);
        } catch (SQLException e) {
            Logger.getLogger(EditCourseServlet.class.getName()).log(Level.SEVERE, "Database error", e);
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/editCourse.jsp").forward(request, response);
        } catch (Exception e) {
            Logger.getLogger(EditCourseServlet.class.getName()).log(Level.SEVERE, "Unexpected error", e);
            request.setAttribute("errorMessage", "Unexpected error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/editCourse.jsp").forward(request, response);
        }
    }

}
