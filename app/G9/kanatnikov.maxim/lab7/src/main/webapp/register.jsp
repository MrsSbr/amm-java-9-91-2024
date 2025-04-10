<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<h1>Регистрация</h1>

<% if (request.getAttribute("errorMessage") != null) { %>
<div style="color: red;">
    <%= request.getAttribute("errorMessage") %>
</div>
<% } %>

<form method="post" action="/register">
    <div>
        <label>Фамилия:</label>
        <input type="text" name="lastName" required>
    </div>
    <div>
        <label>Имя:</label>
        <input type="text" name="firstName" required>
    </div>
    <div>
        <label>Отчество:</label>
        <input type="text" name="patronymic">
    </div>
    <div>
        <label>Город:</label>
        <input type="text" name="city" required>
    </div>
    <div>
        <label>Email:</label>
        <input type="email" name="email" required>
    </div>
    <div>
        <label>Телефон:</label>
        <input type="tel" name="phoneNumber" required>
    </div>
    <div>
        <label>Пароль:</label>
        <input type="password" name="password" required>
    </div>
    <button type="submit">Зарегистрироваться</button>
</form>

<p>Уже есть аккаунт? <a href="/login.jsp">Войти</a></p>
</body>
</html>