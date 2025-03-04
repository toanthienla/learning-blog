<%@page import="model.Material"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"> 
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" 
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
              crossorigin="anonymous">
        <title>Materials Page</title>
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
                            <a class="nav-link" href="/logout">Logout</a>
                        </li>
                    </ul>
                </div>
            </nav>
        </header>

        <main>
            <div class="px-5">
                <h2 class="mt-2">
                    <a href="modules?courseId=<%= request.getAttribute("courseId")%>" style="text-decoration: none; margin-right: 10px">
                        <i class="fa-regular fa-square-caret-left"></i>
                    </a>
                    <%= request.getAttribute("courseName")%>
                </h2>
                <h3 class="mt-2" style="color: #2c3e50; font-weight: 500"><%= request.getAttribute("moduleName")%></h3>
            </div>

            <div class="px-5 mt-4">
                <div class="row">

                    <!--Materials menu list-->
                    <div class="col-md-3 mb-2"> 
                        <div class="list-group list-group-numbered border-right"> 
                            <%
                                List<Material> materials = (List<Material>) request.getAttribute("materials");
                                if (materials != null && !materials.isEmpty()) {
                                    for (Material material : materials) {
                            %>
                            <a href="materials?moduleId=<%= request.getAttribute("moduleId")%>&courseId=<%= request.getAttribute("courseId")%>&materialId=<%= material.getMaterialId()%>" class="list-group-item list-group-item-action">
                                <%= material.getMaterialName()%>
                            </a>
                            <%
                                }
                            } else {
                            %>
                            <div class="list-group-item">No materials found.</div>
                            <%
                                }
                            %>
                        </div>
                    </div> 

                    <!--Material information select-->
                    <div class="col-md-9">
                        <%
                            Material material = (Material) request.getAttribute("material");
                            if (material != null) {
                        %>
                        <h2 ><%= material.getMaterialName()%></h2>
                        <p>
                            <strong>Type:</strong> <%= material.getMaterialType()%><br>
                            <strong>Last Update:</strong> <%= material.getLastUpdate()%>
                        </p>

                        <!--Markdown text here-->
                        <p><strong>Markdown file location: </strong><%= material.getLocation()%></p>

                        <!--Mark Completed and Unmark Completed button-->
                        <!--Use display none to hidden button to solve reading id null-->
                        <%
                            boolean isMaterialStudied = (boolean) request.getAttribute("isUserStudied"); // Get the value from the request attribute
                        %>
                        <% if (isMaterialStudied) {%>
                        <button type="button" class="btn btn-secondary" id="unmarkCompletedBtn" data-course-id="<%= request.getAttribute("courseId")%>" data-material-id="<%= request.getAttribute("materialId")%>">
                            Unmark Completed
                        </button>
                        <button style="display: none" type="button" class="btn btn-primary" id="markCompletedBtn" data-course-id="<%= request.getAttribute("courseId")%>" data-material-id="<%= request.getAttribute("materialId")%>">
                            Mark Completed
                        </button>
                        <% } else {%>
                        <button  type="button" class="btn btn-primary" id="markCompletedBtn" data-course-id="<%= request.getAttribute("courseId")%>" data-material-id="<%= request.getAttribute("materialId")%>">
                            Mark Completed
                        </button>
                        <button style="display: none" type="button" class="btn btn-secondary" id="unmarkCompletedBtn" data-course-id="<%= request.getAttribute("courseId")%>" data-material-id="<%= request.getAttribute("materialId")%>">
                            Unmark Completed
                        </button>
                        <% } %>

                        <%
                        } else {
                        %>
                        <p>Select a material from the sidebar to view its content.</p>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
        </main>

        <script>
            /**
             * Handles the mark complete/unmark complete button click event.
             * 
             * Steps:
             * 1. Check if `completeData` is NULL using `materialId` and `userId`:
             *    - If NULL, show "Mark Completed" button.
             *    - Otherwise, show "Unmark Completed" button.
             * 2. If the user is not enrolled in the course, enroll them.
             * 3. Update the `completeDate` field in the `Study` record.
             * 4. Change the "Mark Completed" button to "Unmark Completed".
             * 5. If the user clicks "Unmark Completed", set the `completeDate` in `Study` to NULL.
             */

            document.getElementById('markCompletedBtn').addEventListener('click', function () {
                const courseId = this.getAttribute('data-course-id');
                const materialId = this.getAttribute('data-material-id');
                fetch('materials?action=mark&courseId=' + courseId + '&materialId=' + materialId, {
                    method: 'POST'
                }).then(response => {
                    window.location.reload();
                });
            });
            document.getElementById('unmarkCompletedBtn').addEventListener('click', function () {
                const courseId = this.getAttribute('data-course-id');
                const materialId = this.getAttribute('data-material-id');
                fetch('materials?action=unmark&courseId=' + courseId + '&materialId=' + materialId, {
                    method: 'POST'
                }).then(response => {
                    window.location.reload();
                });
            });
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
                integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
        crossorigin="anonymous"></script>
    </body>
</html>