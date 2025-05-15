<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Вход</title>
</head>
<body>
<h1>Вход</h1>

<% if (request.getAttribute("errorMessage") != null) { %>
<p style="color:red"><%= request.getAttribute("errorMessage") %></p>
<% } %>

<form action="/login" method="post">
    Имя пользователя: <input type="text" name="name"><br>
    Пароль: <input type="password" name="password"><br>
    <button type="submit">Войти</button>
</form>

<br>
<a href="/register">Регистрация</a>
</body>
</html>

