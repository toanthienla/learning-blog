/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Asus
 */
public class DBContext {

    //Connection object 
    protected Connection conn = null;

    /**
     * Constructor method that try to connect to the database. All information
     * about the connection (database name, username, password) are imported
     * through .env file
     */
    public DBContext() {
        try {
            //Load config from .env
            Dotenv dotenv = Dotenv.configure()
                    .filename(".env") // Ensures it looks for .env in the classpath
                    .load();
            String databaseName = dotenv.get("DATABASE_NAME");
            String username = dotenv.get("DATABASE_USERNAME");
            String password = dotenv.get("DATABASE_PASSWORD");

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbURL = String.format("jdbc:sqlserver://localhost:1433;"
                    + "databaseName=%s;"
                    + "user=%s;"
                    + "password=%s;"
                    + "encrypt=true;trustServerCertificate=true;", databaseName, username, password);
            conn = DriverManager.getConnection(dbURL);
            System.out.println("Connect database successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot found class for SQL Server Driver");
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println("Fail to connect to SQL server");
            System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        new DBContext();
    }
}
