<%@page import="model.Material"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Material List</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            /* CSS để giống giao diện của moduleList.jsp */
            .card {
                transition: transform 0.2s, box-shadow 0.2s;
                cursor: pointer; /* Add cursor pointer to indicate clickable */
            }
            .card:hover {
                transform: translateY(-5px); /* Slightly lift the card on hover */
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* Add a subtle shadow */
            }
            .card-title {
                color: #343a40;
                font-size: 1.25rem;
                margin-bottom: 1rem;
            }
            .card-text {
                color: #6c757d;
                font-size: 0.95rem;
            }
            .btn-container {
                display: flex;
                gap: 8px;
            }
            .btn-primary, .btn-danger, .btn-success, .btn-secondary {
                padding: 6px 12px;
                font-size: 14px;
            }
            .btn-success, .btn-secondary {
                margin-bottom: 20px;
            }
            .alert {
                margin-bottom: 20px;
            }
            .button-group {
                display: flex;
                gap: 10px;
                align-items: center;
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
            <h1>Material List</h1>

            <%-- Hiển thị thông báo lỗi hoặc thành công nếu có --%>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">${errorMessage}</div>
                <c:remove var="errorMessage" scope="session" />
            </c:if>
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success">${successMessage}</div>
                <c:remove var="successMessage" scope="session" />
            </c:if>

            <div class="button-group">
                <form action="addMaterial" method="GET">
                    <input type="hidden" name="moduleId" value="${requestScope.moduleId}">
                    <button type="submit" class="btn btn-success">Add New Material</button>
                </form>

                <a href="moduleList?courseId=${requestScope.courseId}" class="btn btn-secondary">Back</a>
            </div>

            <div class="row">
                <c:forEach var="material" items="${requestScope.materials}">
                    <div class="col-md-4 mb-4">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">${material.materialName}</h5>
                                <p class="card-text">
                                    <strong>Material ID:</strong> ${material.materialId}<br>
                                    <strong>Location:</strong> ${material.location}<br>
                                    <strong>Type:</strong> ${material.materialType}<br>
                                    <strong>Last Update:</strong> ${material.lastUpdate}<br>
                                    <strong>Module ID:</strong> ${material.moduleId}
                                </p>

                                <div class="btn-container">
                                    <a href="editMaterial?id=${material.materialId}" class="btn btn-primary">Edit</a>

                                    <form action="deleteMaterial" method="POST" style="display:inline;">
                                        <input type="hidden" name="id" value="${material.materialId}">
                                        <button type="submit" class="btn btn-danger"
                                                onclick="return confirm('Are you sure you want to delete this material?');">Delete</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <c:if test="${empty requestScope.materials}">
                    <div class="col-12">
                        <p>No materials found.</p>
                    </div>
                </c:if>
            </div>
        </main>

    </body>
</html>