<%@page import="model.Module"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Module List</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
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

        <%-- Kiểm tra quyền truy cập --%>
        <c:set var="userRole" value="${sessionScope.role}" />
        <c:if test="${userRole == null || userRole != 'PUBLISHER'}">
            <c:redirect url="courses.jsp" />
        </c:if>

        <jsp:include page="header.jsp" />

        <main class="container mt-4">
            <h1>Module List</h1>

            <%-- Hiển thị thông báo lỗi hoặc thành công nếu có --%>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">${errorMessage}</div>
                <c:remove var="errorMessage" scope="session" />
            </c:if>
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success">${successMessage}</div>
                <c:remove var="successMessage" scope="session" />
            </c:if>

            <div class="button-group d-flex gap-2 mb-3">
                <form action="addModule" method="GET" style="display:inline;">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" name="courseId" value="${requestScope.courseId}">
                    <button type="submit" class="btn btn-success">Add New Module</button>
                </form>

                <a href="admin" class="btn btn-secondary">Back</a>
            </div>

            <div class="row">
                <c:forEach var="module" items="${requestScope.modules}">
                    <div class="col-md-4 mb-4">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">${module.moduleName}</h5>
                                <p class="card-text">
                                    <strong>Module ID:</strong> ${module.moduleId}<br>
                                    <strong>Last Update:</strong> ${module.lastUpdate}<br>
                                    <strong>Course ID:</strong> ${module.courseId}
                                </p>

                                <div class="btn-container">
                                    <form action="editModule" method="GET" style="display:inline;">
                                        <input type="hidden" name="action" value="edit">
                                        <input type="hidden" name="moduleId" value="${module.moduleId}">
                                        <input type="hidden" name="courseId" value="${module.courseId}">
                                        <button type="submit" class="btn btn-primary">Edit</button>
                                    </form>

                                    <a href="materialList?moduleId=${module.moduleId}" class="btn btn-info">Material List</a>

                                    <form action="deleteModule" method="POST" style="display:inline;">
                                        <input type="hidden" name="moduleId" value="${module.moduleId}">
                                        <input type="hidden" name="courseId" value="${module.courseId}">
                                        <button type="submit" class="btn btn-danger"
                                                onclick="return confirm('Are you sure you want to delete this module?');">Delete</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <c:if test="${empty requestScope.modules}">
                    <div class="col-12">
                        <p>No modules found.</p>
                    </div>
                </c:if>
            </div>
        </main>

    </body>
</html>