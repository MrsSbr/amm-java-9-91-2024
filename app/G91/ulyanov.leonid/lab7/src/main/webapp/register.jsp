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
    <p style="color: green;">${param.message}</p>
</c:if>

<form action="register" method="post" accept-charset="UTF-8">
    <label for="lastName">Фамилия:</label>
    <input type="text" id="lastName" name="lastName" required>
    <br>

    <label for="firstName">Имя:</label>
    <input type="text" id="firstName" name="firstName">
    <br>

    <label for="patronymic">Отчество:</label>
    <input type="text" id="patronymic" name="patronymic">
    <br>

    <label for="email">E-mail:</label>
    <input type="text" id="email" name="email" required>
    <br>

    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required>

    <br>
    <label for="phoneNumber">Номер телефона:</label>
    <input type="text" id="phoneNumber" name="phoneNumber" required>

    <br>
    <button type="submit">Зарегистрироваться</button>
</form>
<p>Вход <a href="login.jsp">здесь</a></p>
</body>
</html>