<%-- 
    Document   : settings
    Created on : Feb 26, 2025, 9:23:09 PM
    Author     : XPS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" 
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
              crossorigin="anonymous">
        <title>Settings Page</title>
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
        </style>
    </head>
    <body>

        <!--Navbar-->
        <header>
            <nav class="navbar navbar-light bg-white">
                <div class="container">
                    <ul class="navbar-nav d-flex flex-row gap-4">
                        <li class="nav-item">
                            <a class="nav-link" href="/BlogLearning">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="dashboard">Dashboard</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="settings">Settings</a>
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
            <h1 class="px-5">Settings</h1>
            <div class="container mt-5">
                <div class="card">
                    <div class="card-body">
                        <form>
                            <div class="mb-3">
                                <label for="username" class="form-label">Username</label>
                                <input type="text" class="form-control" id="username" placeholder="Enter your username">
                            </div>
                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="email" placeholder="Enter your email">
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label">Password</label>
                                <input type="password" class="form-control" id="password" placeholder="Enter your password">
                            </div>
                            <button type="submit" class="btn btn-primary">Save</button>
                            <button type="button" class="btn btn-danger">Delete account</button>
                        </form>
                    </div>
                </div>
            </div>
        </main>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
                integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
        crossorigin="anonymous"></script>
    </body>
</html>
