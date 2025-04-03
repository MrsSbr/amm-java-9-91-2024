<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Вход</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<h1>Вход в систему</h1>

<c:if test="${not empty param.error}">
    <p style="color: red;">${param.error}</p>
</c:if>
<c:if test="${not empty param.message}">
    <p style="color: #54e354;">${param.message}</p>
</c:if>

<form action="signIn" method="post" accept-charset="UTF-8">
    <label for="login">Логин:</label>
    <input type="text" id="login" name="login" required>
    <br>
    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required>
    <br>
    <button type="submit">Войти</button>
</form>
<p><a href="index.jsp">Перейти на главную страницу</a></p>
</body>
</html>