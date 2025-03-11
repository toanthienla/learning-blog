<%@page import="java.util.List"%>
<%@page import="model.Module"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"> 
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" 
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
              crossorigin="anonymous">
        <title>Modules Page</title>
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
            .list-group-item:hover {
                background-color: #f8f9fa; /* Or any other color you prefer */
            }
            .fa-square-caret-left {
                color: #7f8c8d;
                transition: color 0.3s ease; /* Add transition property */
            }

            .fa-square-caret-left:hover {
                color: #2c3e50;
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
                            <a class="nav-link active" href="courses">Courses</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="dashboard">Dashboard</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="settings">Settings</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="admin">Admin</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="sign-in">Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="sign-up">SignUp</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="logout">Logout</a>
                        </li>
                    </ul>
                </div>
            </nav>
        </header>

        <main>
            <h2 class="px-5 mt-2">
                <a href="courses" style="text-decoration: none; margin-right: 10px">
                    <i class="fa-regular fa-square-caret-left"></i>
                </a>
                <%= request.getAttribute("courseName")%>
            </h2>

            <div class="container mt-4">
                <h4 class="mb-3" style="color: #2c3e50;">Modules list</h3>
                    <div class="list-group list-group-numbered list-group-flush">
                        <%
                            // Assuming you have a List<Module> named 'modules' available in the request
                            List<Module> modules = (List<Module>) request.getAttribute("modules");
                            if (modules != null && !modules.isEmpty()) {
                                for (Module module : modules) {
                        %>
                        <a href="materials?moduleId=<%= module.getModuleId()%>&courseId=<%= request.getAttribute("courseId")%>" class="list-group-item list-group-item-action">
                            <%= module.getModuleName()%>
                        </a>
                        <%
                            }
                        } else {
                        %>
                        <div class="list-group-item">No modules found.</div>
                        <%
                            }
                        %>
                    </div>
            </div>
        </main>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
                integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
        crossorigin="anonymous"></script>
    </body>
</html>