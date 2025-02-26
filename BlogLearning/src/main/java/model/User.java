/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Asus
 */
public class User {

    private int id;
    private String username;
    private String email;
    private String password;
    private Role role;
    private int point;

    public User() {
        this.id = -1;
        this.username = "";
        this.email = "";
        this.password = "";
        this.role = null;
        this.point = -1;
    }

    public User(int id, String username, String email, String password, Role role, int point) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.point = point;
    }
    
    public User(int id, String username, String email, String password, Role role) {
        this(id, username, email, password, role, 0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return String.format("ID: %d\nUsername: %s\nEmail: %s\nPassword (hash): %s\nRole: %s\n,Point: %d\n---\n", 
                id, username, email, password, role == Role.PUBLISHER ? "Publisher" : "Reader", point);
    }
}
