package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Material;
import util.DBContext;

public class MaterialsDAO extends DBContext {

    public List<Material> getMaterialsByModuleId(int moduleId) throws SQLException {
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT * FROM Materials WHERE ModuleId = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, moduleId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Material material = new Material();
                    material.setMaterialId(resultSet.getInt("MaterialId"));
                    material.setMaterialName(resultSet.getString("MaterialName"));
                    material.setMaterialType(resultSet.getString("Type"));
                    material.setLastUpdate(resultSet.getDate("LastUpdate"));
                    material.setModuleId(resultSet.getInt("ModuleId"));
                    material.setLocation(resultSet.getString("Location"));
                    materials.add(material);
                }
            }
        }
        return materials;
    }

    public Material getTopMaterialsByModuleId(int moduleId) throws SQLException {
        String sql = "SELECT TOP 1 * FROM Materials WHERE ModuleId = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, moduleId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Material material = new Material();
                    material.setMaterialId(resultSet.getInt("MaterialId"));
                    material.setMaterialName(resultSet.getString("MaterialName"));
                    material.setMaterialType(resultSet.getString("Type"));
                    material.setLastUpdate(resultSet.getDate("LastUpdate"));
                    material.setModuleId(resultSet.getInt("ModuleId"));
                    material.setLocation(resultSet.getString("Location"));
                    return material;
                }
            }
        }
        return null;
    }

    public Material findById(int materialId) throws SQLException {
        String sql = "SELECT * FROM Materials WHERE MaterialId = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, materialId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Material material = new Material();
                    material.setMaterialId(resultSet.getInt("MaterialId"));
                    material.setMaterialName(resultSet.getString("MaterialName"));
                    material.setMaterialType(resultSet.getString("Type"));
                    material.setLastUpdate(resultSet.getDate("LastUpdate"));
                    material.setModuleId(resultSet.getInt("ModuleId"));
                    material.setLocation(resultSet.getString("Location"));
                    return material;
                }
            }
        }
        return null; // Material not found
    }

    public boolean addMaterial(Material material) throws SQLException {
        String sql = "INSERT INTO Materials (MaterialId, MaterialName, Location, Type, LastUpdate, ModuleId) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, material.getMaterialId());
            preparedStatement.setString(2, material.getMaterialName());
            preparedStatement.setString(3, material.getLocation());
            preparedStatement.setString(4, material.getMaterialType());
            preparedStatement.setTimestamp(5, new java.sql.Timestamp(material.getLastUpdate().getTime()));
            preparedStatement.setInt(6, material.getModuleId());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean updateMaterial(Material material) throws SQLException {
        String sql = "UPDATE Materials SET MaterialName = ?, Location = ?, Type = ?, LastUpdate = ?, ModuleId = ? WHERE MaterialId = ?";
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
            preparedStatement.setString(1, material.getMaterialName());
            preparedStatement.setString(2, material.getLocation());
            preparedStatement.setString(3, material.getMaterialType());
            preparedStatement.setTimestamp(4, new java.sql.Timestamp(material.getLastUpdate().getTime()));
            preparedStatement.setInt(5, material.getModuleId());
            preparedStatement.setInt(6, material.getMaterialId());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean deleteMaterial(int materialId) throws SQLException {
        String sql = "DELETE FROM Materials WHERE MaterialId = ?";
        try (PreparedStatement preparedStatement = this.conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, materialId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Kiểm tra xem ModuleId có tồn tại không
    public boolean checkModuleIdExists(int moduleId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Modules WHERE ModuleId = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, moduleId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // Trả về true nếu tồn tại
                }
            }
        }
        return false;
    }
}