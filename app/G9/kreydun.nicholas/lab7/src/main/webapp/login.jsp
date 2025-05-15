<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Вход в систему</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 400px;
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        input {
            width: 90%;
            padding: 10px;
            margin: 5px 0;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .btn {
            padding: 10px 15px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 5px;
        }
        .btn:hover {
            background-color: #45a049;
        }
        .error {
            color: red;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Вход в систему</h2>

    <% if (request.getParameter("error") != null) { %>
    <p class="error">Неверный email или пароль</p>
    <% } %>

    <form action="login" method="post">
        <input type="text" name="email" placeholder="Email" required /><br>
        <input type="password" name="password" placeholder="Пароль" required /><br>
        <input type="submit" value="Войти" class="btn" />
    </form>
    <a href="index.jsp" class="btn">На главную</a>
</div>
</body>
</html>
