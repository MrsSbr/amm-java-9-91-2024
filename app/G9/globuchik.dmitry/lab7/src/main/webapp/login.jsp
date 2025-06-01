<%--
  Created by IntelliJ IDEA.
  User: globu
  Date: 14.04.2025
  Time: 18:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Вход</title>
</head>
<body>
<h2>Вход</h2>
<form action="login" method="post">
  <p>
    <b>Логин:</b><br>
    <input type="text" name="login" size="30" value="${savedLogin}">
  </p>
  <p>
    <b>Пароль:</b><br>
    <input type="password" name="password" size="30">
  </p>
  <p>
    <input type="submit" value="ОК">
  </p>
  <p>
    Нет аккаунта? <a href="/laba7/register">Зарегистрироваться</a>
  </p>
</form>

<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<% if (errorMessage != null) { %>
<div style="color: red;"><%= errorMessage %></div>
<% } %>
</body>
</html>
