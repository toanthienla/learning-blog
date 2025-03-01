<%-- 
    Document   : settings
    Created on : Feb 26, 2025, 9:23:09 PM
    Author     : XPS
--%>
<%@page import="model.Course"%>
<%@page import="java.util.List"%>
<%@page import="model.Role"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" 
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
              crossorigin="anonymous">
        <title>Courses Page</title>
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
                            <a class="nav-link" href="dashboard">Dashboard</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="settings">Settings</a>
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

        <main>
            <h1 class="px-5 mt-2">Courses</h1>

            <div class="container mt-5">
                <div class="row">
                    <%
                        List<Course> courses = (List<Course>) request.getAttribute("courses");
                        if (courses != null) {
                            for (Course course : courses) {
                    %>
                    <div class="col-md-4 mb-4"> 
                        <div class="card" onclick="location.href = 'modules?courseId=<%= course.getCourseId()%>'"> 
                            <div class="card-body">
                                <h5 class="card-title"><%= course.getCourseName()%></h5>
                                <p class="card-text">
                                    <strong>Course ID:</strong> <%= course.getCourseId()%><br>
                                    <strong>Public Date:</strong> <%= course.getPublicDate()%><br>
                                    <strong>Last Update:</strong> <%= course.getLastUpdate()%><br>
                                    <strong>Author ID:</strong> <%= course.getAuthorId()%>
                                </p>
                            </div>
                        </div>
                    </div>
                    <%
                        }
                    } else {
                    %>
                    <div class="col-12">
                        <p>No courses found.</p>
                    </div>
                    <%
                        }
                    %>
                </div>
            </div>
        </main>

        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>