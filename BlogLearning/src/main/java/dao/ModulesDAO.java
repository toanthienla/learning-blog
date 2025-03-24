package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Module;
import util.DBContext;

public class ModulesDAO extends DBContext {

    // Lấy danh sách module theo CourseId
    public List<Module> getModulesByCourseId(int courseId) throws SQLException {
        List<Module> modules = new ArrayList<>();
        String sql = "SELECT * FROM Modules WHERE CourseId = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, courseId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Module module = new Module();
                    module.setModuleId(resultSet.getInt("ModuleId"));
                    module.setModuleName(resultSet.getString("ModuleName"));
                    module.setLastUpdate(resultSet.getDate("LastUpdate"));
                    module.setCourseId(resultSet.getInt("CourseId"));
                    modules.add(module);
                }
            }
        }
        return modules;
    }

    // Tìm module theo ModuleId
    public Module findById(int moduleId) throws SQLException {
        String sql = "SELECT * FROM Modules WHERE ModuleId = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, moduleId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Module module = new Module();
                    module.setModuleId(resultSet.getInt("ModuleId"));
                    module.setModuleName(resultSet.getString("ModuleName"));
                    module.setLastUpdate(resultSet.getDate("LastUpdate"));
                    module.setCourseId(resultSet.getInt("CourseId"));
                    return module;
                }
            }
        }
        return null; // Trả về null nếu không tìm thấy
    }

    // Thêm module mới
    public boolean addModule(Module module) throws SQLException {
        String sql = "INSERT INTO Modules (ModuleId, ModuleName, LastUpdate, CourseId) VALUES (?, ?, GETDATE(), ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, module.getModuleId());
            preparedStatement.setString(2, module.getModuleName());
            preparedStatement.setInt(3, module.getCourseId());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Cập nhật thông tin module
    public boolean updateModule(Module module) throws SQLException {
        // Kiểm tra xem CourseId có tồn tại không
        if (!checkCourseIdExists(module.getCourseId())) {
            throw new SQLException("CourseId " + module.getCourseId() + " does not exist in Courses table.");
        }

        String sql = "UPDATE Modules SET ModuleName = ?, LastUpdate = ?, CourseId = ? WHERE ModuleId = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, module.getModuleName());
            preparedStatement.setTimestamp(2, new java.sql.Timestamp(module.getLastUpdate().getTime()));
            preparedStatement.setInt(3, module.getCourseId());
            preparedStatement.setInt(4, module.getModuleId());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Xóa module
    public boolean deleteModule(int moduleId) throws SQLException {
        String sql = "DELETE FROM Modules WHERE ModuleId = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, moduleId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu có ít nhất 1 hàng bị xóa
        }
    }

    // Kiểm tra xem CourseId có tồn tại không
    public boolean checkCourseIdExists(int courseId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Courses WHERE CourseId = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, courseId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // Trả về true nếu tồn tại, false nếu không
                }
            }
        }
        return false; // Nếu không tìm thấy
    }
}