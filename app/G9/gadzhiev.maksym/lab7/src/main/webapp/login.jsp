<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="auth-form">
    <h2>Вход</h2>

    <form action="login" method="post">
        <div>
            <label>Логин</label>
            <input type="text" name="login" required/>
        </div>
        <div>
            <label>Пароль:</label>
            <input type="password" name="password" required/>
        </div>

        <button type="submit">Войти</button>
    </form>

    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
    <div class="error-message"><%= errorMessage%></div>
    <% } %>

    <div class="auth-links">
        <a href="register.jsp">Регистрация</a>
    </div>

</div>
</body>
</html>
