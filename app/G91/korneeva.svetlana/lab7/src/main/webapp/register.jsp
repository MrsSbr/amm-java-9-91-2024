<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f9e6f9;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .registration-container {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            width: 300px;
            text-align: center;
        }
        h2 {
            color: #ff66b3;
            margin-bottom: 20px;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 2px solid #ff66b3;
            border-radius: 5px;
            font-size: 16px;
            color: #333;
        }
        input[type="submit"] {
            background-color: #ff66b3;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        input[type="submit"]:hover {
            background-color: #ff4da1;
        }
        a {
            color: #ff66b3;
            text-decoration: none;
            font-weight: bold;
        }
        a:hover {
            text-decoration: underline;
        }
        .error-message {
            color: #ff1a1a;
            margin-top: 15px;
            font-size: 14px;
        }
    </style>
</head>
<body>
<div class="registration-container">
    <h2>Регистрация</h2>
    <form action="reg" method="post">
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