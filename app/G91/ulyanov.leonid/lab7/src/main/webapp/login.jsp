<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Вход</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style>
        .error { color: red; }
        .success { color: green; }
    </style>
</head>
<body>
<h1>Вход в систему</h1>

<c:if test="${not empty param.error}">
    <p class="error">${param.error}</p>
</c:if>
<c:if test="${not empty param.message}">
    <p class="success">${param.message}</p>
</c:if>

<form action="login" method="post" accept-charset="UTF-8">
    <div>
        <label for="login">E-mail:</label>
        <input type="text" id="login" name="login" required>
    </div>
    <div>
        <label for="password">Пароль:</label>
        <input type="password" id="password" name="password" required>
    </div>
    <button type="submit">Войти</button>
</form>

<p><a href="books.jsp">Перейти на главную страницу</a></p>
</body>
</html>
