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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Role;
import model.User;
import util.DBContext;
import util.MailService;
import util.Util;

/**
 *
 * @author Asus
 */
public class UserDAO extends DBContext {

    public User createAccount(String username, String email, String password, Role role) {
        try {
            //Get maxID first
            String sql = "SELECT max(UserId) AS maxID FROM Users";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int maxID = 0;
            if (rs.next()) {
                System.out.println(rs.getInt("maxID"));
                maxID = rs.getInt("maxID") + 1;
            }

            //Insert new data to Users table
            sql = "INSERT INTO Users (UserId, UserName, Email, Password, Role) "
                    + "VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, maxID);
            ps.setString(2, username);
            ps.setString(3, email);
            ps.setString(4, Util.hash(password));
            ps.setInt(5, role == Role.PUBLISHER ? 0 : 1);
            int rowAffected = ps.executeUpdate();
            if (rowAffected > 0) {
                //Send email
                MailService mailService = new MailService();
                List<String> to = new ArrayList<>();
                to.add(email);
                mailService.send(to, "Welcome to Blog Learning System", "welcome.html");
                
                //return the newly created user
                return new User(maxID, username, email, Util.hash(password), role);
            }            
        } catch (SQLException e) {

        } catch (Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
   
    public User login(String email, String password) {
        try {
            String sql = "SELECT * FROM Users WHERE email = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, Util.hash(password));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("UserId"),
                        rs.getString("UserName"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("role") == 0 ? Role.PUBLISHER : Role.READER,
                        rs.getInt("Point")
                );
            }
        } catch (SQLException e) {
        }

        return null;
    }

    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
        //dao.createAccount("abc", "abc@gmail.com", "123", Role.READER); //Use real email to receive the welcome mail
    }
}
