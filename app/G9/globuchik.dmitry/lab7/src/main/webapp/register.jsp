<%--
  Created by IntelliJ IDEA.
  User: globu
  Date: 13.04.2025
  Time: 22:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
</head>
<body>
<h2>Регистрация</h2>
<form action="register" method="post">
    <p>
        <b>Логин:</b><br>
        <input type="text" name="login" size="30" value="${savedLogin}">
    </p>
    <p>
        <b>Пароль:</b><br>
        <input type="password" name="password" size="30">
    </p>
    <p>
        <b>Email:</b><br>
        <input type="text" name="email" size="30" value="${savedEmail}">
    </p>
    <p>
        <b>Ник:</b><br>
        <input type="text" name="nickname" size="30" value="${savedNickname}">
    </p>
    <p>
        <b>Номер телефона:</b><br>
        <input type="text" name="phonenumber" size="30" value="${savedPhonenumber}">
    </p>
    <p>
        <input type="submit" value="ОК">
    </p>
    <p>
        Есть аккаунт? <a href="/laba7/login">Войти</a>
    </p>
</form>

<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<% if (errorMessage != null) { %>
<div style="color: red;"><%= errorMessage %></div>
<% } %>
</body>
</html>
