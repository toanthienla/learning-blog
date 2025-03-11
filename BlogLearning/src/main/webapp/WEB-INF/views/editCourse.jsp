<%-- 
    Document   : editCourse
    Created on : Mar 3, 2025, 7:57:50 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Course"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Edit Course</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>

        <div class="container mt-5">
            <h2>Edit Course</h2>

            <%-- Hiển thị lỗi nếu có --%>
            <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
            <% if (errorMessage != null) {%>
            <div class="alert alert-danger"><%= errorMessage%></div>
            <% }%>

            <form action="editCourse" method="post">
                <input type="hidden" name="id" value="<%= (request.getAttribute("id") != null) ? request.getAttribute("id") : ""%>">



                <div class="mb-3">
                    <label for="courseName" class="form-label">Course Name:</label>
                    <input type="text" id="courseName" name="courseName" class="form-control"
                           value="<%= (request.getAttribute("courseName") != null) ? request.getAttribute("courseName") : ""%>" required>
                </div>

                <div class="mb-3">
                    <label for="publicDate" class="form-label">Public Date:</label>
                    <input type="date" id="publicDate" name="publicDate" class="form-control"
                           value="<%= (request.getAttribute("publicDate") != null) ? request.getAttribute("publicDate") : ""%>" required>
                </div>

                <div class="mb-3">
                    <label for="authorId" class="form-label">Author ID:</label>
                    <input type="number" id="authorId" name="authorId" class="form-control"
                           value="<%= (request.getAttribute("authorId") != null) ? request.getAttribute("authorId") : ""%>" required>
                </div>

                <!-- Last Update được tự động, không hiển thị cho người chỉnh sửa -->
                <input type="hidden" name="lastUpdate" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())%>">

                <button type="submit" class="btn btn-primary">Update Course</button>
                <a href="admin" class="btn btn-secondary">Back to Admin</a>

            </form>
        </div>

    </body>
</html>
