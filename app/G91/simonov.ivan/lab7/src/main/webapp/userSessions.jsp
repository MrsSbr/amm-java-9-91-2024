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
    </style>
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
        <tr>
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
            </c:if>
            <td>${session.vehicle.vehicleId}</td>
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
    </c:forEach>
    </tbody>
</table>
</body>
</html>