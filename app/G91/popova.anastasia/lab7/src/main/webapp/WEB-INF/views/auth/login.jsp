<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Вход в систему</title>
    <meta charset="UTF-8">
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f5f5f5;
        }
        .login-container {
            background: white;
            padding: 32px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
        }
        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 24px;
        }
        .form-group {
            margin-bottom: 16px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            color: #666;
        }
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        input[type="submit"] {
            width: 100%;
            padding: 12px;
            background-color: #0056b3;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        input[type="submit"]:hover {
            background-color: #0079bf;
        }
        .register-link {
            display: inline-block;
            padding: 10px 19px;
            background: #f0f0f0;
            color: #0079bf;
            border-radius: 4px;
            text-decoration: none;
            font-weight: bold;
            transition: background 0.2s;"
        }
        .register-link-container {
             text-align: center;
             margin-top: 24px;
        }
        .error-message {
            color: #dc3545;
            text-align: center;
            margin-top: 16px;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h1>Вход в систему</h1>
        <form action="/auth/login" method="post">
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="password">Пароль:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <input type="submit" value="Войти">
        </form>
         <c:if test="${not empty error}">
            <div class="error-message">
                <c:out value="${error}"/>
            </div>
         </c:if>
        <div class = "register-link-container">
            <a href="/auth/register" class="register-link">Зарегистрироваться</a>
        </div>
    </div>
</body>
</html>