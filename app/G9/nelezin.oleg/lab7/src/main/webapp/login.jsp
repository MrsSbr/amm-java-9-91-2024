<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Логин</title>
</head>
<body>
<h2>Вход</h2>
<form action="login" method="post">
    <p>
        <b>Логин:</b><br>
        <input type="text" name="login" size="30">
    </p>
    <p>
        <b>Пароль:</b><br>
        <input type="password" name="password" size="30">
    </p>
    <p>
        <input type="submit" value="ОК">
    </p>
</form>
<p>Нет аккаунта? <a href="registration.jsp">Зарегистрироваться</a></p>
</body>
</html>
