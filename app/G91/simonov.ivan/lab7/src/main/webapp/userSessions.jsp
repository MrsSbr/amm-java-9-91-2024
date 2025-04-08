<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="java.time.format.DateTimeFormatter"%>

<html>
<head>
    <title>Сессии парковки</title>
    <style>
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .user-info { background-color: #e6f7ff; }
        .clickable-row { cursor: pointer; }
        .clickable-row:hover { background-color: #f5f5f5; }
        .session-details {
            display: none;
            margin-top: 20px;
            padding: 15px;
            border: 1px solid #ddd;
            background-color: #f9f9f9;
        }
        .action-buttons {
            margin-top: 10px;
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
            allDetails.forEach(function(detail) {
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
<h1>
    <c:choose>
        <c:when test="${isUser}">Мои сессии парковки</c:when>
        <c:otherwise>Все сессии парковки</c:otherwise>
    </c:choose>
</h1>

<c:if test="${not empty param.message}">
    <p style="color: #54e354;">${param.message}</p>
</c:if>
<c:if test="${not empty param.error}">
    <p style="color: red;">${param.error}</p>
</c:if>

<table>
    <thead>
    <tr>
        <c:if test="${not isUser}">
            <th>ID сессии</th>
            <th class="user-info">ID пользователя</th>
            <th class="user-info">ФИО</th>
            <th class="user-info">Логин</th>
            <th class="user-info">Пароль</th>
            <th>ID автомобиля</th>
        </c:if>
        <th>Регистрационный номер</th>
        <th>Модель</th>
        <th>Бренд</th>
        <th>Цвет</th>
        <th>Цена</th>
        <th>Дата въезда</th>
        <th>Дата выезда</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${sessions}" var="session">
        <tr class="clickable-row" onclick="showSessionDetails(${session.sessionId})">
            <c:if test="${not isUser}">
                <td>${session.sessionId}</td>
                <td>${session.user.userId}</td>
                <td class="user-info">
                        ${session.user.lastName}
                        ${session.user.firstName}
                    <c:choose>
                        <c:when test="${not empty session.user.patronymic}">
                            ${session.user.patronymic}
                        </c:when>
                        <c:otherwise>
                            <em>-----</em>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="user-info">
                        ${session.user.login}
                </td>
                <td class="user-info">
                        ${session.user.password}
                </td>
                <td>${session.vehicle.vehicleId}</td>
            </c:if>
            <td>${session.vehicle.registrationNumber}</td>
            <td>${session.vehicle.model}</td>
            <td>${session.vehicle.brand}</td>
            <td>${session.vehicle.colour}</td>
            <td>${session.parkingPrice}</td>
            <td>${session.entryDate.format(DateTimeFormatter.ofPattern('dd.MM.yyyy HH:mm'))}</td>
            <td>
                <c:choose>
                    <c:when test="${not empty session.exitDate}">
                        ${session.exitDate.format(DateTimeFormatter.ofPattern('dd.MM.yyyy HH:mm'))}
                    </c:when>
                    <c:otherwise>
                        <em>-----</em>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>

        <!-- Детали сессии (скрыты по умолчанию) -->
        <tr>
            <td colspan="${not isUser ? 13 : 7}" style="padding: 0;">
                <div id="details-${session.sessionId}" class="session-details">
                    <h3>Детали сессии #${session.sessionId}</h3>
                    <p><strong>Пользователь:</strong>
                            ${session.user.lastName} ${session.user.firstName}
                        <c:if test="${not empty session.user.patronymic}">${session.user.patronymic}</c:if>
                    </p>
                    <p><strong>Автомобиль:</strong>
                            ${session.vehicle.brand} ${session.vehicle.model},
                            ${session.vehicle.colour},
                            ${session.vehicle.registrationNumber}
                    </p>
                    <p><strong>Въезд:</strong> ${session.entryDate.format(DateTimeFormatter.ofPattern('dd.MM.yyyy HH:mm'))}</p>
                    <p><strong>Выезд:</strong>
                        <c:choose>
                            <c:when test="${not empty session.exitDate}">
                                ${session.exitDate.format(DateTimeFormatter.ofPattern('dd.MM.yyyy HH:mm'))}
                            </c:when>
                            <c:otherwise>
                                <em>Еще на парковке</em>
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <p><strong>Стоимость:</strong> ${session.parkingPrice}</p>

                    <c:if test="${not isUser}">
                        <div class="action-buttons">
                            <form action="updateSession" method="post" style="display: inline;">
                                <input type="hidden" name="sessionId" value="${session.sessionId}">
                                <button type="submit">Изменить</button>
                            </form>

                            <form action="deleteSession" method="post" style="display: inline;">
                                <input type="hidden" name="sessionId" value="${session.sessionId}">
                                <button type="submit" onclick="return confirm('Вы уверены, что хотите удалить эту сессию?')">Удалить</button>
                            </form>
                        </div>
                    </c:if>
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:choose>
    <c:when test="${not isUser}">
        <form action="redirectUser" method="get">
            <button type="submit" class="return-link">Вернуться к списку действий</button>
        </form>
    </c:when>
    <c:otherwise>
        <p><a href="index.jsp" class="return-link">Перейти на главную страницу</a></p>
    </c:otherwise>
</c:choose>
</body>
</html>