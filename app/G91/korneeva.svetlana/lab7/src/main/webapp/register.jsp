<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
    <jsp:include page="style.jsp"/>
</head>
<body>
<div class="registration-container">
    <h2>Регистрация</h2>
    <form action="/register" method="post">
        <p>
            <input type="text" name="email" placeholder="Email" required>
        </p>
        <p>
            <input type="password" name="password" placeholder="Пароль" required>
        </p>
        <p>
            <input type="submit" value="Зарегистрироваться">
        </p>
    </form>
    <p>Уже есть аккаунт? <a href="/login">Войти</a></p>
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
    <div class="error-message"><%= errorMessage %></div>
    <% } %>
</div>
</body>
</html>