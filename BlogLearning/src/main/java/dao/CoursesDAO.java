package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Course; // Assuming you have a Course model class
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

}
