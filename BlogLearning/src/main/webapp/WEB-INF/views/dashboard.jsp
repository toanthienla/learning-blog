<%-- 
    Document   : dashboard
    Created on : Feb 27, 2025, 7:31:40 AM
    Author     : Asus
--%>

<%@page import="model.MaterialProgress"%>
<%@page import="model.Module"%>
<%@page import="model.CourseProgress"%>
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
                overflow-x: auto; /* Enable horizontal scrolling */
                -webkit-overflow-scrolling: touch; /* Smooth scrolling on mobile */
                scrollbar-width: none; /* Hide scrollbar in Firefox */
            }
            .navbar-nav::-webkit-scrollbar {
                display: none; /* Hide scrollbar in Chrome/Safari */
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
        <jsp:include page="header.jsp" />
        <%
            int userId = (int) request.getAttribute("id");
            String username = (String) request.getAttribute("username");
            String role = (Role) request.getAttribute("role") == Role.PUBLISHER ? "Publisher" : "Reader";
            int point = (int) request.getAttribute("point");
            int rank = (int) request.getAttribute("rank");
            List<User> top3 = (List<User>) request.getAttribute("top3");
            if (userId != 0 && !username.isEmpty() && !role.isEmpty() && point >= 0 && rank != 0) {
        %>
        
        <main class="container">
            <div>
                <div class="row">
                    <div class="col-lg-12 shadow p-3 mb-5 bg-body-tertiary rounded">
                        <h2>Welcome</h2>
                        <p><%= role + " " + username%></p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-3 shadow p-3 mb-5 bg-body-tertiary rounded">
                        <h3>Total point:</h3>
                        <p><%= point%></p>
                    </div>
                    <div class="col-lg-3 shadow p-3 mb-5 bg-body-tertiary rounded">
                        <h3>Current Rank</h3>
                        <p>#<%= rank%></p>
                    </div>
                    <div class="col-lg-6 shadow p-3 mb-5 bg-body-tertiary rounded">
                        <h3>Top point <%=role%></h3>
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
                    if (request.getAttribute("detailCourses") != null) {
                        List<CourseProgress> detailCourses = (List<CourseProgress>) request.getAttribute("detailCourses");
                %>
                <div class="row">
                    <%
                        for (CourseProgress course : detailCourses) {
                    %>
                    <div class="col-lg-12 shadow-sm p-3 mb-5 bg-body-tertiary rounded">
                        <h3>Course: <%= course.getCourseName()%></h3>
                        <progress value="<%= course.getProgress()%>" max="100"></progress>
                        <%
                            for (Module module : course.getModules()) {
                        %>
                        <div class="shadow p-3 mb-5 bg-body-tertiary rounded">
                            <h4>Module: <%=module.getModuleName()%></h4>
                            <%
                                for (MaterialProgress material : module.getMaterials()) {
                            %>
                            <div class="shadow-lg p-3 mb-5 bg-body-tertiary rounded">
                                <h5><%=material.getMaterialName()%></h5>
                                <p>Type: <%= material.getMaterialType()%></p>
                                <%
                                    if (material.getCompleteDate() != null) {
                                %>
                                <p>Complete Date: <%= material.getCompleteDate()%></p>
                                <%
                                } else {
                                %>
                                <p>Uncompleted</p>
                                <%
                                    }
                                %>

                            </div>
                            <%
                                }
                            %>
                        </div>
                        <%
                            }
                        %>
                    </div>
                    <%
                        }
                    %>
                </div>
                <%
                    }
                %>
                <%
                    if (request.getAttribute("ownCourses") != null) {
                        List<Course> ownCourses = (List<Course>) request.getAttribute("ownCourses");
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
