<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Module</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .button-group {
            display: flex;
            gap: 10px;
            align-items: center;
        }
        .form-container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
        }
    </style>
</head>
<body>

<%-- Kiểm tra quyền truy cập --%>
<c:set var="userRole" value="${sessionScope.role}" />
<c:if test="${userRole == null || userRole != 'PUBLISHER'}">
    <c:redirect url="moduleList?courseId=${courseId}" />
</c:if>

<jsp:include page="header.jsp" />

<div class="form-container">
    <h2>Add Module</h2>

    <%-- Hiển thị lỗi nếu có --%>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <%-- Hiển thị thông báo thành công nếu có --%>
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>

    <%-- Form thêm module --%>
    <form action="addModule" method="post">
        <div class="mb-3">
            <label for="moduleId" class="form-label">Module ID:</label>
            <input type="number" id="moduleId" name="moduleId" class="form-control" 
                   value="${moduleId}" required>
        </div>

        <div class="mb-3">
            <label for="moduleName" class="form-label">Module Name:</label>
            <input type="text" id="moduleName" name="moduleName" class="form-control" 
                   value="${moduleName}" required>
        </div>

        <div class="mb-3">
            <label for="courseId" class="form-label">Course ID:</label>
            <input type="number" id="courseId" name="courseId" class="form-control" 
                   value="${courseId}" readonly>
        </div>

        <div class="button-group">
            <button type="submit" class="btn btn-primary">Create</button>
            <a href="moduleList?courseId=${courseId}" class="btn btn-secondary">Back</a>
        </div>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>