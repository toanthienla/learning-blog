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
public class StudyDAO extends DBContext {

    public boolean isMaterialStudiedByUser(int userId, int materialId) throws SQLException {
        String sql = "SELECT 1 FROM Study WHERE UserId = ? AND MaterialId = ? AND CompleteDate IS NOT NULL";
        try ( PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, materialId);
            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public boolean markMaterialAsStudied(int userId, int materialId) throws SQLException {
        String sql = "UPDATE Study SET CompleteDate = GETDATE() WHERE UserId = ? AND MaterialId = ?";
        try ( PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, materialId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean unmarkMaterialAsStudied(int userId, int materialId) throws SQLException {
        String sql = "UPDATE Study SET CompleteDate = NULL WHERE UserId = ? AND MaterialId = ?";
        try ( PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, materialId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
