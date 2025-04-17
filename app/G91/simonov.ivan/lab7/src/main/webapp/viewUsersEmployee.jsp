<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Пользователи</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        .action-buttons button {
            margin-right: 10px;
            padding: 5px 10px;
        }
    </style>
    <script>
        function showSessionDetails(sessionId) {
            // Скрываем все открытые детали
            var allDetails = document.querySelectorAll('.session-details');
            allDetails.forEach(function (detail) {
                detail.style.display = 'none';
            });

            // Показываем выбранные детали
            var detailDiv = document.getElementById('details-' + sessionId);
            if (detailDiv) {
                detailDiv.style.display = 'block';
            }
        }
    </script>
</head>
<body>
<h1>Пользователи</h1>

<c:if test="${not empty param.message}">
    <p style="color: #54e354;">${param.message}</p>
</c:if>
<c:if test="${not empty param.error}">
    <p style="color: red;">${param.error}</p>
</c:if>

<table>
    <thead>
    <tr>
        <th>ID пользователя</th>
        <th>Фамилия</th>
        <th>Имя</th>
        <th>Отчество</th>
        <th>Логин</th>
        <th>Роль</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.userId}</td>
            <td>${user.lastName}</td>
            <td>${user.firstName}</td>
            <td>
                <c:choose>
                    <c:when test="${not empty user.patronymic}">
                        ${user.patronymic}
                    </c:when>
                    <c:otherwise>
                        <em>-----</em>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>${user.login}</td>
            <td>${user.role}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<p><a href="employeeActions.jsp" class="return-link">Перейти к списку действий</a></p>
</body>
</html>
