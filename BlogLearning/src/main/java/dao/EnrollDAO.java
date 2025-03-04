/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.DBContext;

/**
 *
 * @author XPS
 */
public class EnrollDAO extends DBContext {

    public boolean insertUser(int userId, int courseId) throws SQLException {
        String sql = "INSERT INTO Enroll (UserId, CourseId) VALUES (?, ?)";
        try ( PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, courseId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean isUserEnrollCourse(int userId, int courseId) throws SQLException {
        String sql = "SELECT 1 FROM Enroll WHERE UserId = ? AND CourseId = ?";
        try ( PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, courseId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if a row is found
            }
        }
    }
}
