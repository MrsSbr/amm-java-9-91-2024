<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Регистрация</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<h1>Регистрация</h1>

<c:if test="${not empty param.error}">
    <p style="color: red;">${param.error}</p>
</c:if>
<c:if test="${not empty param.message}">
    <p style="color: #51d251;">${param.message}</p>
</c:if>

<form action="registerUser" method="post" accept-charset="UTF-8">
    <label for="lastName">Фамилия:</label>
    <input type="text" id="lastName" name="lastName" required>
    <br>
    <label for="firstName">Имя:</label>
    <input type="text" id="firstName" name="firstName">
    <br>
    <br>
    <label for="patronymic">Отчество:</label>
    <input type="text" id="patronymic" name="patronymic">
    <br>
    <label for="login">Логин:</label>
    <input type="text" id="login" name="login" required>
    <br>
    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required>
    <br>
    <button type="submit">Зарегистрироваться</button>
</form>
<p><a href="index.jsp">Перейти на главную страницу</a></p>
</body>
</html>