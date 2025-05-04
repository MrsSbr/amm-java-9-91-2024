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
        <input type="text" name="login">
    </p>
    <p>
        <b>Пароль:</b><br>
        <input type="password" name="password">
    </p>
    <p>
        <input type="submit" value="ОК">
    </p>
</form>
<p><a href="/register">Зарегистрироваться</a></p>
<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<% if (errorMessage != null) { %>
<div style="color: red;"><%= errorMessage %></div>
<% } %>
</body>
</html>