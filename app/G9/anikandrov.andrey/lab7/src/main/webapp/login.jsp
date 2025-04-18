<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Логин</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .login-container {
            margin-top: 50px;
            border: 1px solid #4682b4;
            border-radius: 5px;
            padding: 20px;
            width: 250px;
        }
        .btn {
            display: block;
            width: 100%;
            padding: 8px;
            margin: 5px 0;
            font-size: 14px;
            color: white;
            background-color: #4682b4;
            border: none;
            border-radius: 3px;
            text-align: center;
            text-decoration: none;
            cursor: pointer;
            box-sizing: border-box;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 5px;
            margin: 5px 0;
            border: 1px solid #4682b4;
            border-radius: 3px;
            box-sizing: border-box;
        }
        .error-message {
            color: red;
            text-align: center;
        }
    </style>
</head>
<body>

<div class="login-container">
    <h2>Вход</h2>
    <form action="login" method="post">
        <p>
            <b>Логин:</b><br>
            <input type="text" name="login">
        </p>
        <p>
            <b>Пароль:</b><br>
            <input type="password" name="password">
        </p>
        <input type="submit" class="btn" value="Войти">
        <a href="register.jsp" class="btn">Регистрация</a>
    </form>

    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
    <div class="error-message"><%= errorMessage %></div>
    <% } %>
</div>
</body>
</html>