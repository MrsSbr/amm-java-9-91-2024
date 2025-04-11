<%@ page import="ru.vsu.amm.java.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Профиль пользователя</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 600px;
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #333;
        }
        .input-group {
            margin-bottom: 15px;
        }
        label {
            font-weight: bold;
            display: block;
        }
        input[type="text"], input[type="email"], input[type="password"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .btn {
            display: inline-block;
            margin-top: 10px;
            padding: 10px 15px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            border: none;
            cursor: pointer;
        }
        .btn:hover {
            background-color: #45a049;
        }
        .error {
            color: red;
        }
        .success {
            color: green;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Ваш профиль</h1>

    <%
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
    %>

    <%
        String success = (String) session.getAttribute("success");
        String error = (String) session.getAttribute("error");
        if (success != null) {
    %>
    <p class="success"><%= success %></p>
    <%
            session.removeAttribute("success");
        }
        if (error != null) {
    %>
    <p class="error"><%= error %></p>
    <%
            session.removeAttribute("error");
        }
    %>

    <form action="updateUser" method="post">
        <div class="input-group">
            <label>ID пользователя:</label>
            <input type="text" value="<%= user.getId() %>" readonly>
        </div>

        <div class="input-group">
            <label>Имя пользователя:</label>
            <input type="text" name="username" value="<%= user.getUsername() %>" required>
        </div>

        <div class="input-group">
            <label>Email:</label>
            <input type="email" name="email" value="<%= user.getEmail() %>" required>
        </div>

        <div class="input-group">
            <label>Новый пароль (оставьте пустым, если не меняете):</label>
            <input type="password" name="password">
        </div>

        <div class="input-group">
            <label>Подтвердите новый пароль:</label>
            <input type="password" name="confirmPassword">
        </div>

        <button type="submit" class="btn">Обновить данные</button>
        <a href="logout" class="btn" style="background-color: red;">Выйти</a>
    </form>

    <form action="deleteUser" method="post" onsubmit="return confirm('Вы уверены, что хотите удалить аккаунт? Это действие необратимо!');">
        <button type="submit" class="btn" style="background-color: darkred;">Удалить аккаунт</button>
    </form>

    <a href="index" class="btn">На главную</a>

</div>

</body>
</html>
