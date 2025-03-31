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
        <input type="text" name="name" size="30">
    </p>
    <p>
        <b>Пароль:</b><br>
        <input type="password" name="password" size="30">
    </p>
    <p>
        <input type="submit" value="ОК">
    </p>
</form>
<p><a href="/login">Войти</a></p>
</body>
</html>