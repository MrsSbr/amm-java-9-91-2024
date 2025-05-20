<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Аутентификация</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5dc;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .auth-container {
            background-color: #e9ffd9;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 300px;
            text-align: center;
        }

        h2 {
            color: #556b2f;
            margin-bottom: 20px;
        }

        .form-group {
            margin-bottom: 15px;
            text-align: left;
        }

        label {
            display: block;
            margin-bottom: 5px;
            color: #6b8e23;
            font-weight: bold;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 8px;
            box-sizing: border-box;
        }

        .btn {
            background-color: #8fbc8f;
            color: white;
            border: none;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 10px 0;
            cursor: pointer;
            border-radius: 20px;
            transition: background-color 0.3s;
            width: 100%;
        }

        .btn-register {
            background-color: #d2b48c;
        }

        .btn:hover {
            background-color: #7ca57c;
        }

        .btn-register:hover {
            background-color: #c0a47b;
        }

        .error {
            color: #ff0000;
            margin-bottom: 15px;
        }

        .button-group {
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div class="auth-container">
    <h2>Вход в систему</h2>

    <% if (request.getParameter("error") != null) { %>
    <div class="error">Неверный логин или пароль</div>
    <% } %>

    <form action="register" method="POST">
        <div class="form-group">
            <label for="username">Логин:</label>
            <input type="text" id="username" name="j_username" required>
        </div>

        <div class="form-group">
            <label for="password">Пароль:</label>
            <input type="password" id="password" name="j_password" required>
        </div>

        <div class="button-group">
            <button type="submit" class="btn">Войти</button>
            <button type="button" onclick="location.href='register.jsp'" class="btn btn-register">Регистрация</button>
        </div>
    </form>
</div>
</body>
</html>