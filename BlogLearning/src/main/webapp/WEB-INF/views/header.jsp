<%-- 
    Document   : header
    Created on : Mar 19, 2025, 11:31:47 PM
    Author     : hoqua
--%>

<%@page import="model.Role"%>
<%@page import="model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    User user = (User) session.getAttribute("account");
    int role = -1;
    String username = "Guest";
    if (user != null) {
        role = user.getRole() == Role.PUBLISHER ? 0 : 1;
        username = user.getUsername();
    }
    String currentPage = request.getRequestURI();
%>
<header>
    <nav class="navbar navbar-light bg-white">
        <div class="container">
            <ul class="navbar-nav d-flex flex-row gap-4">
                <li class="nav-item">
                    <a class="nav-link <%= currentPage.equals(request.getContextPath() + "/") || currentPage.equals(request.getContextPath()) ? "active" : ""%>" href="/BlogLearning">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <%= currentPage.contains("courses") || currentPage.contains("modules") || currentPage.contains("materials") ? "active" : ""%>" href="courses">Courses</a>
                </li>



                <% if (user != null) {%>
                <li class="nav-item">
                    <a class="nav-link  <%= currentPage.contains("dashboard") ? "active" : ""%>" href="dashboard">Dashboard</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <%= currentPage.contains("settings") ? "active" : ""%>" href="settings">Settings</a>
                </li>

                <% if (role == 0) {%> 
                <li class="nav-item">
                    <a class="nav-link <%= currentPage.contains("admin") ? "active" : ""%>" href="admin">Admin</a>
                </li>
                <% } %>
                <li class="nav-item">
                    <a class="nav-link" href="logout">Logout</a>
                </li>
                <% } else { %>
                <li class="nav-item">
                    <a class="nav-link" href="sign-in">Login</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="sign-up">SignUp</a>
                </li>
                <% }%>
            </ul>
        </div>
    </nav>
</header>
