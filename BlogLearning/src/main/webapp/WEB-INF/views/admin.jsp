<%@page import="model.Course"%>
<%@page import="java.util.List"%>
<%@page import="model.Role"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin - Manage Courses</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<%
    // Kiểm tra quyền truy cập
    Role userRole = (Role) session.getAttribute("role");
    if (userRole == null || userRole != Role.PUBLISHER) {
        response.sendRedirect("courses.jsp");
        return;
    }
%>

<header>
    <nav class="navbar navbar-light bg-white">
        <div class="container">
            <ul class="navbar-nav d-flex flex-row gap-4">
                <li class="nav-item"><a class="nav-link" href="/BlogLearning">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="courses">Courses</a></li>
                <li class="nav-item"><a class="nav-link" href="dashboard">Dashboard</a></li>
                <li class="nav-item"><a class="nav-link active" href="admin">Admin</a></li>
                <li class="nav-item"><a class="nav-link" href="/logout">Logout</a></li>
            </ul>
        </div>
    </nav>
</header>

<main class="container mt-4">
    <h1>Manage Courses</h1>

    <!-- Form chuyển hướng đến addCourse.jsp -->
    <form action="editCourse" method="GET">
        <input type="hidden" name="action" value="add">
        <button type="submit" class="btn btn-success mb-3">Add New Course</button>
    </form>

    <div class="row">
        <%
            List<Course> courses = (List<Course>) request.getAttribute("courses");
            if (courses != null && !courses.isEmpty()) {
                for (Course course : courses) {
        %>
        <div class="col-md-4 mb-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title"><%= course.getCourseName() %></h5>
                    <p class="card-text">
                        <strong>Course ID:</strong> <%= course.getCourseId() %><br>
                        <strong>Public Date:</strong> <%= course.getPublicDate() %><br>
                        <strong>Last Update:</strong> <%= course.getLastUpdate() %><br>
                        <strong>Author ID:</strong> <%= course.getAuthorId() %>
                    </p>

                    <!-- Form chuyển hướng đến editCourse.jsp -->
                    <form action="editCourse" method="GET" style="display:inline;">
                        <input type="hidden" name="action" value="edit">
                        <input type="hidden" name="id" value="<%= course.getCourseId() %>">
                        <button type="submit" class="btn btn-primary">Edit</button>
                    </form>

                    <!-- Link xóa khóa học -->
                    <a href="deleteCourse?id=<%= course.getCourseId() %>"
                       class="btn btn-danger"
                       onclick="return confirm('Are you sure you want to delete this course?');">Delete</a>
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
</main>

</body>
</html>
