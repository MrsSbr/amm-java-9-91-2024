<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Логин</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f8ff;
            margin: 0;
            padding: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .header-image {
            width: 150px;
            height: 150px;
            margin-bottom: 10px;
        }
        .login-container {
            background-color: #e6f2ff;
            border: 2px solid #4682b4;
            border-radius: 10px;
            padding: 20px;
            width: 300px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .button {
            display: inline-block;
            padding: 10px 20px;
            font-size: 16px;
            color: white;
            background-color: #4682b4;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            text-align: center;

        }

        .button:hover {
            background-color: #1e3c72;
        }
        h2 {
            color: #1e3c72;
            text-align: center;
            margin-top: 0;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 8px;
            margin: 5px 0 15px 0;
            border: 1px solid #4682b4;
            border-radius: 4px;
            box-sizing: border-box;
        }
        input[type="submit"], .register-btn {
            background-color: #4682b4;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            width: 100%;
            margin-top: 10px;
        }
        input[type="submit"]:hover, .register-btn:hover {
            background-color: #36648b;
        }
        .error-message {
            color: red;
            text-align: center;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<img src="" alt="Логотип" class="header-image">

<div class="login-container">
    <h2>Вход</h2>
    <form action="login" method="post">
        <p>
            <b>Логин:</b><br>
            <input type="text" name="login" size="30">
        </p>
        <p>
            <b>Пароль:</b><br>
            <input type="password" name="password" size="30">
        </p>
        <p>
            <input type="submit" value="Вход">
        </p>
    </form>

    <p>
        <a href="register.jsp" class="button">Регистрация</a>
    </p>


    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
    <div class="error-message"><%= errorMessage %></div>
    <% } %>
</div>





</body>
</html>