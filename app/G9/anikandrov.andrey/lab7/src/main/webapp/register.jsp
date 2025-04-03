<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .registration-container {
            border: 1px solid #4682b4;
            border-radius: 5px;
            padding: 20px;
            width: 250px;
        }
        .btn {
            display: block;
            width: 100%;
            padding: 8px;
            margin: 10px 0;
            font-size: 14px;
            color: white;
            background-color: #4682b4;
            border: none;
            border-radius: 3px;
            text-align: center;
            cursor: pointer;
        }
        input[type="text"],
        input[type="password"]
        {
            width: 100%;
            padding: 5px;
            margin: 5px 0 10px 0;
            border: 1px solid #4682b4;
            border-radius: 3px;
            box-sizing: border-box;
        }
        .error-message {
            color: red;
            text-align: center;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<img src="logo.png" alt="Логотип" width="200">

<div class="registration-container">
    <h2>Регистрация</h2>
    <form action="register" method="post">
        <label>
            <b>Имя пользователя:</b>
            <input type="text" name="login" required>
        </label>
        <label>
            <b>Пароль:</b>
            <input type="password" name="password" required>
        </label>
        <input type="submit" class="btn" value="Зарегистрироваться">
    </form>

    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
    <div class="error-message"><%= errorMessage %></div>
    <% } %>
</div>
</body>
</html>