<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Регистрация</title>
</head>
<body>
<h1>Регистрация</h1>

<c:if test="${not empty param.error}">
    <p style="color: red;">${param.error}</p>
</c:if>
<c:if test="${not empty param.message}">
    <p style="color: #51d251;">${param.message}</p>
</c:if>

<form action="register" method="post">
    <label for="login">Логин:</label>
    <input type="text" id="login" name="login" required>
    <br>
    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required>
    <br>
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required>
    <br>
    <label for="phoneNumber">Номер телефона:</label>
    <input type="text" id="phoneNumber" name="phoneNumber">
    <br>
    <button type="submit">Зарегистрироваться</button>
</form>
</body>
</html>
