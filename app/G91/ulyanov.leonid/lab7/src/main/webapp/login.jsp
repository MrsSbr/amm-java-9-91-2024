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
    <p style="color: green;">${param.message}</p>
</c:if>

<form action="login" method="post" accept-charset="UTF-8">
    <label for="login">Email:</label>
    <input type="text" id="login" name="login" required>
    <br>
    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required>
    <br>
    <button type="submit">Войти</button>
</form>
<p>Регистрация <a href="register.jsp">здесь</a></p>
</body>
</html>