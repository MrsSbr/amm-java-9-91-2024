<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Список пользователей</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2>Список пользователей</h2>
    <a href="${pageContext.request.contextPath}/users/new" class="btn btn-success mb-3">Добавить пользователя</a>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Логин</th>
            <th>Имя</th>
            <th>Телефон</th>
            <th>Email</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.userId}</td>
                <td>${user.login}</td>
                <td>${user.username}</td>
                <td>${user.phoneNumber}</td>
                <td>${user.email}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/users/edit?id=${user.userId}"
                       class="btn btn-sm btn-primary">Редактировать</a>
                    <a href="${pageContext.request.contextPath}/users/delete?id=${user.userId}"
                       class="btn btn-sm btn-danger"
                       onclick="return confirm('Вы уверены?')">Удалить</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>