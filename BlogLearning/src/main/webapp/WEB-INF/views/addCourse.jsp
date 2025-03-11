<%-- 
    Document   : addCourse
    Created on : Mar 3, 2025, 7:53:41 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add New Course</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>

        <div class="container mt-5">
            <h2>Add New Course</h2>

            <%-- Hiển thị lỗi nếu có --%>
            <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
            <% if (errorMessage != null) {%>
            <div class="alert alert-danger"><%= errorMessage%></div>
            <% }%>

            <form action="addCourse" method="post">
                <div class="mb-3">
                    <label for="courseId" class="form-label">Course ID:</label>
                    <input type="number" id="courseId" name="courseId" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label for="courseName" class="form-label">Course Name:</label>
                    <input type="text" id="courseName" name="courseName" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label for="publicDate" class="form-label">Public Date:</label>
                    <input type="date" id="publicDate" name="publicDate" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label for="authorId" class="form-label">Author ID:</label>
                    <input type="number" id="authorId" name="authorId" class="form-control" required>
                </div>

                <button type="submit" class="btn btn-success">Add Course</button>
                <a href="admin" class="btn btn-secondary">Back to Admin</a>


            </form>
        </div>

    </body>
</html>
