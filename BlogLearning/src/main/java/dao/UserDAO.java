/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import jakarta.mail.MessagingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    public User createAccount(String username, String email, String password, Role role) throws SQLException, MessagingException, IllegalArgumentException {
        //Sanitize email (email is case insesitive)
        email = email.toLowerCase();

        //Check if the current email or username has been registered
        String sql = "SELECT UserName FROM Users WHERE UserName = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String dbUsername = rs.getString("UserName");
            if (dbUsername != null && !dbUsername.isEmpty()) {
                throw new IllegalArgumentException("This username has been used!");
            }
        }

        //Get maxID first
        sql = "SELECT max(UserId) AS maxID FROM Users";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        int maxID = 0;
        if (rs.next()) {
            System.out.println("Current max ID: " + rs.getInt("maxID"));
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

        throw new SQLException("Failed to create new account");
    }

    public User login(String username, String password) throws SQLException, IllegalArgumentException {
        String sql = "SELECT * FROM Users WHERE UserName = ? AND Password = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
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

        throw new IllegalArgumentException("Usename or password incorrect!");
    }

    public User updateUserPasswordByUsername(String username, String oldPassword, String newPassword) throws SQLException {
        // Hash the passwords
        String hashedOldPassword = Util.hash(oldPassword);
        String hashedNewPassword = Util.hash(newPassword);

        // Check if the old password matches the stored password
        String sql = "SELECT * FROM Users WHERE UserName = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String storedPassword = rs.getString("Password");
            if (!hashedOldPassword.equals(storedPassword)) {
                throw new IllegalArgumentException("Incorrect old password!");
            }
        } else {
            throw new IllegalArgumentException("User not found!");
        }

        // Update the password
        sql = "UPDATE Users SET Password = ? WHERE UserName = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, hashedNewPassword);
        ps.setString(2, username);

        int rowsAffected = ps.executeUpdate();

        if (rowsAffected > 0) {
            // Retrieve the updated user object
            sql = "SELECT * FROM Users WHERE UserName = ?"; // Assign the sql query to a variable
            ps = conn.prepareStatement(sql); // Reuse the PreparedStatement
            ps.setString(1, username); // Set the username parameter
            rs = ps.executeQuery();

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
        }

        return null; // Return null if update fails or user retrieval fails
    }

    public int getUserRank(int userId, Role role) {
        String sql = "SELECT COUNT(*) AS [Top] FROM Users WHERE Point >= (SELECT Point FROM Users WHERE UserId = ?) AND Role = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, role == Role.PUBLISHER ? 0 : 1);
            ResultSet rs = ps.executeQuery();
            int top = 0;
            if (rs.next()) {
                top = rs.getInt("Top");
            }
            return top;
        } catch (Exception e) {
            e.getMessage();
            return 0;
        }

    }

    public List<User> getTopThreeUser(Role role) {
        List<User> top3User = new ArrayList<>();
        String sql = "SELECT TOP 3 * FROM Users Where Role = ? ORDER BY Point DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, role == Role.PUBLISHER ? 0 : 1);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                top3User.add(new User(
                        rs.getInt("UserId"),
                        rs.getString("UserName"),
                        rs.getString("Email"),
                        rs.getString("Password"),
                        rs.getInt("role") == 0 ? Role.PUBLISHER : Role.READER,
                        rs.getInt("Point")));
            }
            return top3User;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }

    }

    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
        try {
            System.out.println(dao.getUserRank(2, Role.READER));
        } catch (Exception e) {
            e.getMessage();
        }

        //dao.createAccount("abc", "dangnh.ce190707@gmail.com", "123", Role.READER); //Use real email to receive the welcome mail
    }
}
