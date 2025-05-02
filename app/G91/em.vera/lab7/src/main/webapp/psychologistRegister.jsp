<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация психолога</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
    <style>
        :root {
            --gradient-start: #8e2de2;
            --gradient-end:   #4a00e0;
            --bg-card:        #ffffff;
            --text-dark:      #333333;
            --text-light:     #f5f5f5;
            --input-bg:       #f2f2f7;
            --border-color:   #ddd;
        }
        * { box-sizing: border-box; margin:0; padding:0; }
        body {
            background: #f2f2f7;
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            color: var(--text-dark);
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
        }
        .card {
            width: 400px;
            background: var(--bg-card);
            border-radius: 1rem;
            box-shadow: 0 4px 16px rgba(0,0,0,0.1);
            overflow: hidden;
        }
        .card-header {
            background: linear-gradient(135deg, var(--gradient-start), var(--gradient-end));
            padding: 2rem 1.5rem;
            text-align: center;
        }
        .card-header h2 {
            color: var(--text-light);
            margin-bottom: .5rem;
            font-size: 1.8rem;
        }
        .card-header p {
            color: rgba(255,255,255,0.85);
            font-size: .95rem;
        }
        .card-body {
            padding: 2rem 1.5rem;
        }
        .card-body form {
            display: flex;
            flex-direction: column;
            gap: 1rem;
        }
        .input-group { position: relative; }
        .input-group input,
        .input-group select {
            width: 100%;
            padding: .75rem 1rem  .75rem 2.5rem;
            border: 1px solid var(--border-color);
            border-radius: .5rem;
            background: var(--input-bg);
            font-size: .95rem;
            color: var(--text-dark);
        }
        .input-group .icon {
            position: absolute;
            top: 50%;
            left: .75rem;
            transform: translateY(-50%);
            font-size: 1rem;
            color: #888;
        }
        .btn {
            width: 100%;
            padding: .75rem;
            border: none;
            border-radius: .5rem;
            background: linear-gradient(135deg, var(--gradient-start), var(--gradient-end));
            color: white;
            font-size: 1rem;
            font-weight: bold;
            cursor: pointer;
            transition: opacity .3s;
        }
        .btn:hover { opacity: .9; }
        .footer {
            text-align: center;
            margin-top: 1rem;
            font-size: .9rem;
        }
        .footer a {
            color: var(--gradient-end);
            text-decoration: none;
            font-weight: bold;
        }
        .footer a:hover { text-decoration: underline; }
        .error {
            color: #c0392b;
            font-size: .9rem;
            margin-bottom: .5rem;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="card">
    <div class="card-header">
        <h2>Регистрация психолога</h2>
        <p>Заполните все поля</p>
    </div>
    <div class="card-body">
        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/psychologist/register" method="post">
            <div class="input-group">
                <span class="icon">&#128100;</span>
                <input type="text" name="name" placeholder="Имя" autocomplete="off" required />
            </div>
            <div class="input-group">
                <span class="icon">&#128100;</span>
                <input type="text" name="surname" placeholder="Фамилия" autocomplete="off" required />
            </div>
            <div class="input-group">
                <span class="icon">&#128197;</span>
                <input type="date" name="birthdate" required />
            </div>
            <div class="input-group">
                <span class="icon">&#9794;</span>
                <select name="gender" required>
                    <option value="" disabled selected>Пол</option>
                    <option value="FEMALE">Женский</option>
                    <option value="MALE">Мужской</option>
                </select>
            </div>
            <div class="input-group">
                <span class="icon">&#128338;</span>
                <input type="number" name="experience" placeholder="Стаж (лет)" min="0" required />
            </div>
            <div class="input-group">
                <span class="icon">&#128101;</span>
                <input type="text" name="login" placeholder="Логин" autocomplete="off" required />
            </div>
            <div class="input-group">
                <span class="icon">&#128274;</span>
                <input type="password" name="password" placeholder="Пароль" autocomplete="new-password" required />
            </div>
            <button type="submit" class="btn">Зарегистрироваться</button>
        </form>
    </div>
    <div class="footer">
        Уже есть аккаунт?
        <a href="${pageContext.request.contextPath}/psychologist/login">Войти</a>
    </div>
    <div class="footer">
        <a href="${pageContext.request.contextPath}/client/login">Войти как клиент</a>
    </div>
</div>
</body>
</html>
