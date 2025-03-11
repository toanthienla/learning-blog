package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Course; // Assuming you have a Course model class
import model.CourseProgress;
import model.MaterialProgress;
import model.Module;
import util.DBContext;

/**
 *
 * @author XPS
 */
public class CoursesDAO extends DBContext {

    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT CourseId, CourseName, PublicDate, LastUpdate, AuthorId FROM Courses"; // Assuming your table name is Courses
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Course course = new Course();
            course.setCourseId(resultSet.getInt("CourseId"));
            course.setCourseName(resultSet.getString("CourseName"));
            course.setPublicDate(resultSet.getDate("PublicDate"));
            course.setLastUpdate(resultSet.getDate("LastUpdate"));
            course.setAuthorId(resultSet.getInt("AuthorId"));
            courses.add(course);
        }
        return courses;
    }

    public Course findById(int courseId) throws SQLException {
        String sql = "SELECT * FROM Courses WHERE CourseId = ?";
        try ( PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, courseId);
            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Course course = new Course();
                    course.setCourseId(resultSet.getInt("CourseId"));
                    course.setCourseName(resultSet.getString("CourseName"));
                    course.setPublicDate(resultSet.getDate("PublicDate"));
                    course.setLastUpdate(resultSet.getDate("LastUpdate"));
                    course.setAuthorId(resultSet.getInt("AuthorId"));
                    return course;
                }
            }
        }
        return null; // Course not found
    }

    public List<Course> getOwnCourse(int userId) throws SQLException {
        String sql = "SELECT * FROM Courses Where AuthorId = ?";
        List<Course> courses = new ArrayList<>();
        try ( PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Course course = new Course();
                    course.setCourseId(resultSet.getInt("CourseId"));
                    course.setCourseName(resultSet.getString("CourseName"));
                    course.setPublicDate(resultSet.getDate("PublicDate"));
                    course.setLastUpdate(resultSet.getDate("LastUpdate"));
                    course.setAuthorId(resultSet.getInt("AuthorId"));
                    courses.add(course);
                }
            }
        }
        return courses;
    }

    public List<CourseProgress> getDetailCourseEnroll(int userId) throws SQLException {
        List<CourseProgress> courses = new ArrayList<>();
        String sql = "SELECT e.Progress, e.CourseId, c.CourseName, c.PublicDate, c.LastUpdate, c.AuthorId, mo.ModuleId, mo.ModuleName, mo.LastUpdate AS ModuleLastUpdate, m.[Location], m.MaterialId, m.MaterialName, m.[Type], m.LastUpdate AS MaterialLastUpdate, s.CompleteDate FROM Enroll e\n"
                + "JOIN Courses c ON e.CourseId = c.CourseId\n"
                + "JOIN Modules mo ON c.CourseId = mo.CourseId\n"
                + "JOIN Materials m ON mo.ModuleId = m.ModuleId\n"
                + "JOIN Study s ON m.MaterialId = s.MaterialId\n"
                + "WHERE e.UserId = ? AND s.UserId = ?";

        try ( PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, userId);
            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                Map<Integer, CourseProgress> courseMap = new HashMap<>();
                while (resultSet.next()) {
                    int courseId = resultSet.getInt("CourseId");
                    CourseProgress course = courseMap.get(courseId);
                    if (course == null) {
                        course = new CourseProgress(resultSet.getDouble("Progress"), courseId, resultSet.getString("CourseName"), resultSet.getDate("PublicDate"), resultSet.getDate("LastUpdate"), resultSet.getInt("AuthorId"));
                        courseMap.put(courseId, course);
                    }

                    int moduleId = resultSet.getInt("ModuleId");
                    Module module = course.getModule(moduleId);
                    if (module == null) {
                        module = new Module(moduleId, resultSet.getString("ModuleName"), resultSet.getDate("ModuleLastUpdate"), courseId);
                        course.addModule(module);
                    }
                    MaterialProgress material = new MaterialProgress(resultSet.getDate("CompleteDate"), resultSet.getString("Location"), resultSet.getInt("MaterialId"), resultSet.getString("MaterialName"), resultSet.getString("Type"), resultSet.getDate("MaterialLastUpdate"), moduleId);
                    module.addMaterial(material);
                }
                courses.addAll(courseMap.values());
            }
        }
        return courses;
    }

    // Thêm khóa học mới
    public void addCourse(Course course) throws SQLException {
        String sql = "INSERT INTO Courses (CourseId, CourseName, PublicDate, LastUpdate, AuthorId) VALUES (?, ?, ?, ?, ?)";
        try ( PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, course.getCourseId());
            preparedStatement.setString(2, course.getCourseName());
            preparedStatement.setDate(3, new java.sql.Date(course.getPublicDate().getTime()));
            preparedStatement.setDate(4, new java.sql.Date(course.getLastUpdate().getTime()));
            preparedStatement.setInt(5, course.getAuthorId());
            preparedStatement.executeUpdate();
        }
    }

    // Cập nhật thông tin khóa học
    public void updateCourse(Course course) throws SQLException {
        String sql = "UPDATE Courses SET CourseName = ?, PublicDate = ?, LastUpdate = ?, AuthorId = ? WHERE CourseId = ?";
        try ( PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
            preparedStatement.setString(1, course.getCourseName());
            preparedStatement.setDate(2, new java.sql.Date(course.getPublicDate().getTime()));
            preparedStatement.setDate(3, new java.sql.Date(course.getLastUpdate().getTime()));
            preparedStatement.setInt(4, course.getAuthorId());
            preparedStatement.setInt(5, course.getCourseId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Xóa khóa học
    public void deleteCourse(int courseId) throws SQLException {
        String sql = "DELETE FROM Courses WHERE CourseId = ?";
        try ( PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, courseId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {

    }
}
