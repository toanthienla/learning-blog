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
import model.Module;
import util.DBContext;

/**
 *
 * @author XPS
 */
public class ModulesDAO extends DBContext {

    public List<Module> getModulesByCourseId(int courseId) throws SQLException {
        List<Module> modules = new ArrayList<>();
        String sql = "SELECT * FROM Modules WHERE CourseId = ?"; // Assuming your table name is Courses
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, courseId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Module module = new Module(); // Create Module object
            module.setModuleId(resultSet.getInt("ModuleId"));
            module.setModuleName(resultSet.getString("ModuleName"));
            module.setLastUpdate(resultSet.getDate("LastUpdate"));
            module.setCourseId(resultSet.getInt("CourseId"));
            modules.add(module);
        }
        return modules;
    }
}
