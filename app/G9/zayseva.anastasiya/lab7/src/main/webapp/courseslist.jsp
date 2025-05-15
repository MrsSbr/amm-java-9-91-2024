<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Список курсов</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2>Список курсов</h2>
    <a href="${pageContext.request.contextPath}/courses/new" class="btn btn-primary mb-3">Добавить курс</a>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Название</th>
            <th>Описание</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="course" items="${courses}">
            <tr>
                <td>${course.courseId}</td>
                <td>${course.title}</td>
                <td>${course.description}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/courses/edit?id=${course.courseId}" class="btn btn-sm btn-warning">Редактировать</a>
                    <a href="${pageContext.request.contextPath}/courses/delete?id=${course.courseId}" class="btn btn-sm btn-danger">Удалить</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>