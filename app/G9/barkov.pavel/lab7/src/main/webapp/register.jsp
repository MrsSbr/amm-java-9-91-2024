<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 500px;
            margin: 0 auto;
            padding: 25px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #555;
        }
        input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        input:focus {
            outline: none;
            border-color: #4CAF50;
            box-shadow: 0 0 5px rgba(76,175,80,0.3);
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 12px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
            font-size: 16px;
        }
        .links {
            text-align: center;
            margin-top: 20px;
        }
        .links a {
            color: #666;
            text-decoration: none;
            font-size: 14px;
        }
        .links a:hover {
            color: #2196F3;
        }
        button:hover {
            background-color: #45a049;
        }
        .required::after {
            content: "*";
            color: red;
            margin-left: 3px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2 style="text-align: center; color: #333;">Форма регистрации</h2>
    <form action="register" method="post">
        <div class="form-group">
            <label class="required" for="Name">ФИО:</label>
            <input type="text" id="Name" name="Name" required
                   placeholder="Иванов Иван Иванович">
        </div>

        <div class="form-group">
            <label class="required" for="Document">Документ:</label>
            <input type="text" id="Document" name="Document" required
                   placeholder="Серия и номер документа">
        </div>

        <div class="form-group">
            <label class="required" for="Email">Email:</label>
            <input type="email" id="Email" name="Email" required
                   placeholder="example@domain.com">
        </div>

        <div class="form-group">
            <label class="required" for="Phone">Телефон:</label>
            <input type="tel" id="Phone" name="Phone" required
                   placeholder="+7 (999) 123-45-67"
                   pattern="\+7\s?[\(]{0,1}9[0-9]{2}[\)]{0,1}\s?\d{3}[-]{0,1}\d{2}[-]{0,1}\d{2}">
        </div>

        <div class="form-group">
            <label class="required" for="Password">Пароль:</label>
            <input type="password" id="Password" name="Password" required
                   placeholder="Не менее 8 символов"
                   minlength="8">
        </div>
        <button type="submit">Зарегистрироваться</button>
    </form>
    <div class="links">
        <a href="login.jsp">Уже есть аккаунт</a><br>
    </div>
</div>
</body>
</html>