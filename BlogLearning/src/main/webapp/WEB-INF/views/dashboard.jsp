<%-- 
    Document   : dashboard
    Created on : Feb 27, 2025, 7:31:40 AM
    Author     : Asus
--%>

<%@page import="model.Course"%>
<%@page import="java.util.List"%>
<%@page import="model.User"%>
<%@page import="model.Role"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dashboard</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" 
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
              crossorigin="anonymous">
        <style>
            .navbar-nav {
                margin: auto;
                padding-top: 1rem;
                font-size: 18px;
            }
            .nav-link {
                color: #bdc3c7;
            }
            .nav-link:hover {
                color: #7f8c8d;
            }
            .card {
                transition: transform 0.2s, box-shadow 0.2s;
                cursor: pointer; /* Add cursor pointer to indicate clickable */
            }
            .card:hover {
                transform: translateY(-5px); /* Slightly lift the card on hover */
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* Add a subtle shadow */
            }
        </style>
    </head>
    <body>
        <%
            int userId = (int) request.getAttribute("id");
            String username = (String) request.getAttribute("username");
            String role = (Role) request.getAttribute("role") == Role.PUBLISHER ? "Publisher" : "Reader";
            int point = (int) request.getAttribute("point");
            int rank = (int) request.getAttribute("rank");
            List<User> top3 = (List<User>) request.getAttribute("top3");
            if (userId != 0 && !username.isEmpty() && !role.isEmpty() && point >= 0 && rank != 0) {
        %>
        <header>
            <nav class="navbar navbar-light bg-white">
                <div class="container">
                    <ul class="navbar-nav d-flex flex-row gap-4">
                        <li class="nav-item">
                            <a class="nav-link" href="/BlogLearning">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="courses">Courses</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="dashboard">Dashboard</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="settings">Settings</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Admin</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="sign-in">Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="sign-up">SignUp</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/logout">Logout</a>
                        </li>
                    </ul>
                </div>
            </nav>
        </header>
        <main class="container">
            <div>
                <div class="row">
                    <div class="col-lg-12 shadow-none p-3 mb-5 bg-body-tertiary rounded">
                        <h2>Welcome</h2>
                        <p><%= role + " " + username%></p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-3 shadow p-3 mb-5 bg-body-tertiary rounded">
                        <h2>Total point:</h2>
                        <p><%= point%></p>
                    </div>
                    <div class="col-lg-3 shadow p-3 mb-5 bg-body-tertiary rounded">
                        <h2>Current Rank</h2>
                        <p>#<%= rank%></p>
                    </div>
                    <div class="col-lg-6 shadow p-3 mb-5 bg-body-tertiary rounded">
                        <h2>Top point Users</h2>
                        <%
                            int top = 1;
                            for (User user : top3) {
                        %>
                        <p><%= top%> <%= user.getUsername()%> <%= user.getPoint()%></p>
                        <%
                                top++;
                            }
                        %>
                    </div>
                </div>
                <%
                    List<Course> ownCourses = (List<Course>) request.getAttribute("ownCourses");
                    if (ownCourses != null) {
                %>
                <h2>Pulished Courses</h2>
                <div class="row">
                    <%
                        for (Course course : ownCourses) {
                    %>
                    <div class="col-lg-6 shadow p-3 mb-5 bg-body-tertiary rounded">
                        <h5>Course ID: <%= course.getCourseId()%></h5>
                        <p><%= course.getCourseName()%></p>
                        <p>Pulish Date: <%= course.getPublicDate()%></p>
                        <p>Last Update: <%= course.getLastUpdate()%></p>
                    </div>
                    <%
                        }
                    %>
                </div>
                <%
                    }
                %>
            </div>




        </main>
        <%
            }
        %>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
                integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
        crossorigin="anonymous"></script>
    </body>
</html>
