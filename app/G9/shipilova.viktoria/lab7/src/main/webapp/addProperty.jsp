<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Добавить найденную вещь</title>
</head>
<body>
<h1>Добавить найденную вещь</h1>
<form action="addProperty" method="post">
    <%
        request.setAttribute("propertyTypes", ru.vsu.amm.java.enams.PropertyTypeName.values());
    %>
    <select name="propertyType">
        <c:forEach var="type" items="${propertyTypes}">
            <option value="${type}">${type}</option>
        </c:forEach>
    </select>
    <label for="placeOfFinding">Место нахождения:</label>
    <input type="text" id="placeOfFinding" name="placeOfFinding" required>
    <br>
    <label for="description">Описание:</label>
    <textarea id="description" name="description" required></textarea>
    <br>
    <button type="submit">Добавить</button>
</form>
<li><a href="index.jsp">Перейти на главную страницу</a></li>
</body>
</html>
