<%-- 
    Document   : signIn
    Created on : Feb 26, 2025, 10:53:21 PM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign in</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" 
              rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" 
              crossorigin="anonymous">
        <style>
            body {
                margin: 0;
                padding: 20px;
                height: 100vh; /* Full height of the viewport */
                display: flex;
                justify-content: center; /* Centers horizontally */
                align-items: center; /* Centers vertically */
            }

            .container {
                max-width: 600px;
                width: 100%;
                padding: 20px;
                background-color: #f8f9fa; /* Optional background */
                border-radius: 10px; /* Optional rounded corners */
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Optional shadow */
            }

            h1 {
                text-align: center;
            }

            input, select {
                max-width: 500px;
                min-width: 250px;
            }

            label, input, select {
                margin-left: 30px;
            }

            #submit {
                display: block;
                margin: 20px auto 0;
                max-width: 400px;
                min-width: 300px;
            }

            p {
                text-align: center;
                margin: 10px;
                font-size: 0.8em;
            }
        </style>
    </head>
    <body>
        <%
            String errMsg = (String) session.getAttribute("errMsg");
            if (errMsg != null) {
                session.removeAttribute("errMsg"); // Clear after displaying
            }
        %>

        <!-- Modal box notification: show up when sign up has error -->
        <div class="modal fade" id="sign-in-failed" tabindex="-1" aria-labelledby="popupLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="popupLabel">ERROR SIGNING IN!</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <%= errMsg%>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Sign up form -->
        <div class="container"> 
            <h1>SIGN IN</h1>
            <form action="sign-in" method="POST">
                <label class="form-label">Email</label>
                <input type="email" id="email" name="email" class="form-control" 
                       placeholder="Enter email" required>                
                
                <label class="form-label">Password</label>
                <input type="password" id="password" name="password" class="form-control" 
                       placeholder="Enter your password" required>
                
                <input type="submit" value="Sign up" class="btn btn-primary" id="submit">
                <p>Already have an account? <a href="sign-in">Sign in</a> instead</p>
            </form>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                let errMsg = "<%= errMsg != null ? errMsg : ""%>";
                //console.log("Error message:", errMsg); // Debugging line

                if (errMsg.trim() !== "") {
                    let popup = new bootstrap.Modal(document.getElementById("sign-in-failed"));
                    popup.show();
                }
            });
        </script>
</html>
