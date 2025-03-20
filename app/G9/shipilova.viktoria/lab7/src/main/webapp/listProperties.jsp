<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="ru.vsu.amm.java.entities.User" %>
<html>
<head>
    <title>Список найденных вещей</title>
</head>
<body>
<h1>Список найденных вещей</h1>

<c:if test="${not empty param.message}">
    <p style="color: #54e354;">${param.message}</p>
</c:if>
<c:if test="${not empty param.error}">
    <p style="color: red;">${param.error}</p>
</c:if>

<% User user = (User) session.getAttribute("user"); %>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Тип</th>
        <th>Дата находки</th>
        <th>Время находки</th>
        <th>Место находки</th>
        <th>Описание</th>
        <c:if test="${editable}">
            <th>Действия</th>
        </c:if>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="property" items="${properties}">
        <tr>
            <td>${property.id}</td>
            <td>${property.propertyType.propertyTypeName}</td>
            <td>${property.dateOfFinding}</td>
            <td>${property.timeOfFinding}</td>
            <td>${property.placeOfFinding}</td>
            <td>${property.description}</td>
            <td>
                <c:if test="${editable}">
                    <form action="updateProperty" method="post">
                        <input type="hidden" name="propertyId" value="${property.id}">
                        <button type="submit">Отметить как возвращённое</button>
                    </form>
                </c:if>

            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${not editable}">
<p style="color: blue;">Войдите, чтобы получить возможность изменить статус найденной вещи.</p>
</c:if>
<li><a href="index.jsp">Перейти на главную страницу</a></li>