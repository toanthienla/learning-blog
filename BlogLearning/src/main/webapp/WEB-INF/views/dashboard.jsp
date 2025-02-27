<%-- 
    Document   : dashboard
    Created on : Feb 27, 2025, 7:31:40 AM
    Author     : Asus
--%>

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
    </head>
    <body>
        <%
            String username = (String) request.getAttribute("username");
            String role = (Role) request.getAttribute("role") == Role.PUBLISHER ? "Publisher" : "Reader";
            if (!username.isEmpty() && !role.isEmpty()) {
        %>
        <h1>Hello, <%= role + " " + username%></h1>
        <%
            }
        %>
        <a href="log-out" class="btn btn-primary">Logout</a>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
                integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
                crossorigin="anonymous"></script>
    </body>
</html>
