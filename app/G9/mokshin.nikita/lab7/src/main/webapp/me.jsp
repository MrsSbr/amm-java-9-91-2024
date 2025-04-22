<%@ page import="ru.vsu.amm.java.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Профиль</title>
</head>
<body>
<% User user = (User) request.getAttribute("user"); %>
<h1>Профиль</h1>
<h3>Имя:</h3>
<p><%= user.getName()%></p>
<h3>Логин</h3>
<p><%= user.getLogin()%></p>
</body>
</html>
