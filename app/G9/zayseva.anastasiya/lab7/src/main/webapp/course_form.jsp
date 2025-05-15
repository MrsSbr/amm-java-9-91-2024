<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${empty course ? 'Добавить курс' : 'Редактировать курс'}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2>${empty course ? 'Добавить новый' : 'Редактировать'} курс</h2>
    <form action="${pageContext.request.contextPath}/courses/save" method="post">
        <c:if test="${not empty course}">
            <input type="hidden" name="id" value="${course.courseId}">
        </c:if>
        <div class="mb-3">
            <label for="title" class="form-label">Название</label>
            <input type="text" class="form-control" id="title" name="title"
                   value="${course.title}" required>
        </div>
        <div class="mb-3">
            <label for="description" class="form-label">Описание</label>
            <textarea class="form-control" id="description" name="description"
                      rows="3">${course.description}</textarea>
        </div>
        <button type="submit" class="btn btn-primary">Сохранить</button>
        <a href="${pageContext.request.contextPath}/courses/list" class="btn btn-secondary">Отмена</a>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>