/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Course;
import model.Material;
import model.Module;
import util.DBContext;

/**
 *
 * @author XPS
 */
public class MaterialsDAO extends DBContext {

    public List<Material> getMaterialsByModuleId(int moduleId) throws SQLException {
        List<Material> materials = new ArrayList<>();
        String sql = "SELECT * FROM Materials WHERE ModuleId = ?";
        try ( PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, moduleId);
            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
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
        try ( PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, moduleId);
            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
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
        try ( PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, materialId);
            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
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
}
