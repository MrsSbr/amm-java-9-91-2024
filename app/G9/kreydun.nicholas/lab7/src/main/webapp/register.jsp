<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        .container { max-width: 400px; margin: auto; background: white; padding: 20px; border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); text-align: center; }
        h1 { color: #333; }
        input { width: 100%; padding: 10px; margin: 5px 0; border: 1px solid #ddd; border-radius: 5px; }
        .btn { display: block; width: 100%; margin-top: 10px; padding: 10px; background-color: #4CAF50;
            color: white; text-decoration: none; border: none; border-radius: 5px; cursor: pointer; }
        .btn:hover { background-color: #45a049; }
        .message { padding: 10px; margin-bottom: 10px; border-radius: 5px; }
        .success { background-color: #d4edda; color: green; border: 1px solid #c3e6cb; }
        .error { background-color: #f8d7da; color: red; border: 1px solid #f5c6cb; }
    </style>
</head>
<body>

<%
    String successMessage = (String) session.getAttribute("success");
    String errorMessage = (String) session.getAttribute("error");
    session.removeAttribute("success");
    session.removeAttribute("error");
%>

<div class="container">
    <h1>Регистрация</h1>

    <% if (successMessage != null) { %>
    <div class="message success"><%= successMessage %></div>
    <% } %>
    <% if (errorMessage != null) { %>
    <div class="message error"><%= errorMessage %></div>
    <% } %>

    <form action="createUser" method="post">
        <input type="text" name="username" placeholder="Имя пользователя" required>
        <input type="email" name="email" placeholder="Электронная почта" required>
        <input type="password" name="password" placeholder="Пароль" required>
        <button type="submit" class="btn">Зарегистрироваться</button>
    </form>

    <p>Уже есть аккаунт? <a href="login.jsp">Войти</a></p>
    <a href="index" class="btn">На главную</a>
</div>

</body>
</html>
