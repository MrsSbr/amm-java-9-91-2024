<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Главная страница</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h1>Добро пожаловать в систему образования!</h1>
    <p class="lead mt-3">Вы можете:</p>

    <div class="list-group mt-3">
        <a href="${pageContext.request.contextPath}/courses/list" class="list-group-item list-group-item-action">
            <i class="bi bi-book"></i> Просмотреть список всех курсов
        </a>
        <a href="${pageContext.request.contextPath}/enrollments/list" class="list-group-item list-group-item-action">
            <i class="bi bi-plus-circle"></i> Подписка на курс
        </a>
        <a href="${pageContext.request.contextPath}/users/list" class="list-group-item list-group-item-action">
            <i class="bi bi-people"></i> Просмотреть список пользователей
        </a>
        <div class="list-group-item">
            <a href="${pageContext.request.contextPath}/auth/login" class="btn btn-outline-primary me-2">Войти</a>
            <a href="${pageContext.request.contextPath}/auth/register" class="btn btn-primary">Зарегистрироваться</a>
        </div>
    </div>
</div>

<!-- Bootstrap Icons -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>