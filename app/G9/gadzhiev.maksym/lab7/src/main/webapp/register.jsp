<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
</head>
<body>
<div class="auth-form">
    <h2>Регистрация</h2>
    <form action="register" method="post">
        <div>
            <label>Логин:</label>
            <input type="text" name="login" required/>
        </div>
        <div>
            <label>Пароль:</label>
            <input type="password" name="password" required/>
        </div>
        <div>
            <label>Подтвердите пароль:</label>
            <input type="password" name="confirmPassword" required/>
        </div>
        <div>
            <label>Email:</label>
            <input type="email" name="email" required/>
        </div>
        <div>
            <label>Номер телефона:</label>
            <input type="text" name="phoneNumber" required/>
        </div>

        <button type="submit">Зарегистрироваться</button>
    </form>

    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
    <div class="error-message"><%= errorMessage%></div>
    <% } %>

    <div class="auth-links">
        <a href="login">Уже есть аккаунт? Войти</a>
    </div>
</div>
</body>
</html>