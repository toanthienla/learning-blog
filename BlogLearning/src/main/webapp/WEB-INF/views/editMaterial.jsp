<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Edit Material</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>

        <div class="container mt-5">
            <h2>Edit Material</h2>

            <%-- Hiển thị lỗi nếu có --%>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">${errorMessage}</div>
                <c:remove var="errorMessage" scope="request" />
            </c:if>

            <form action="editMaterial" method="post" enctype="multipart/form-data">
                <input type="hidden" name="id" value="${id}">

                <div class="mb-3">
                    <label for="materialName" class="form-label">Material Name:</label>
                    <input type="text" id="materialName" name="materialName" class="form-control"
                           value="${materialName}" required>
                </div>

                <div class="mb-3">
                    <label for="location" class="form-label">Current Location:</label>
                    <input type="text" id="location" name="location" class="form-control"
                           value="${location}" readonly>
                </div>

                <div class="mb-3">
                    <label for="file" class="form-label">Upload New File (optional):</label>
                    <input type="file" id="file" name="file" class="form-control">
                </div>

                <div class="mb-3">
                    <label for="materialType" class="form-label">Material Type:</label>
                    <select id="materialType" name="materialType" class="form-select" required>
                        <option value="Video" ${materialType == 'Video' ? 'selected' : ''}>Video</option>
                        <option value="Article" ${materialType == 'Article' ? 'selected' : ''}>Article</option>
                        <option value="PDF" ${materialType == 'PDF' ? 'selected' : ''}>PDF</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="lastUpdate" class="form-label">Last Update (yyyy-MM-dd):</label>
                    <input type="date" id="lastUpdate" name="lastUpdate" class="form-control"
                           value="${lastUpdate}">
                </div>

                <div class="mb-3">
                    <label for="moduleId" class="form-label">Module ID:</label>
                    <input type="number" id="moduleId" name="moduleId" class="form-control"
                           value="${moduleId}" required>
                </div>

                <button type="submit" class="btn btn-primary">Update Material</button>
                <a href="materialList?moduleId=${moduleId}" class="btn btn-secondary">Back</a>
            </form>
        </div>

    </body>
</html>