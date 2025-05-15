<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Список записей на курсы</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2>Список записей на курсы</h2>
    <a href="${pageContext.request.contextPath}/enrollments/new" class="btn btn-primary mb-3">Добавить запись</a>

    <table class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Пользователь</th>
            <th>Курс</th>
            <th>Дата записи</th>
            <th>Статус</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="enrollment" items="${enrollments}">
            <tr>
                <td>${enrollment.userCourseId}</td>
                <td>
                    <c:forEach var="user" items="${users}">
                        <c:if test="${user.userId == enrollment.userId}">${user.username}</c:if>
                    </c:forEach>
                </td>
                <td>
                    <c:forEach var="course" items="${courses}">
                        <c:if test="${course.courseId == enrollment.courseId}">${course.title}</c:if>
                    </c:forEach>
                </td>
                <td>${enrollment.subscriptionDate}</td>
                <td>${enrollment.enrollmentStatus}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/enrollments/edit?id=${enrollment.userCourseId}" class="btn btn-sm btn-warning">Редактировать</a>
                    <a href="${pageContext.request.contextPath}/enrollments/delete?id=${enrollment.userCourseId}" class="btn btn-sm btn-danger">Удалить</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>