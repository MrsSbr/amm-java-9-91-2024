<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Вход</title>
    <jsp:include page="style.jsp"/>
</head>
<body>
<div class="registration-container">
    <h2>Вход</h2>
    <form action="/login" method="post">
        <p>
            <input type="text" name="email" placeholder="Email" required>
        </p>
        <p>
            <input type="password" name="password" placeholder="Пароль" required>
        </p>
        <p>
            <input type="submit" value="Войти">
        </p>
    </form>
    <p>Нет аккаунта? <a href="/register">Зарегистрироваться</a></p>
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
    <div class="error-message"><%= errorMessage %></div>
    <% } %>
</div>
</body>
</html>