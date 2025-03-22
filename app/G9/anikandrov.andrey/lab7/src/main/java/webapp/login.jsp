<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Авторизация</title>
    <style>
        .auth-form {
            width: 300px;
            margin: auto;
            text-align: center;
        }
        .success {
            color: green;
        }
        .error {
            color: red;
        }
    </style>
</head>
<body>
<div class="auth-form">
    <div style="text-align: center; margin-bottom: 20px;">
        <img src="${pageContext.request.contextPath}/path/to/logo.png" alt="Описание изображения" style="width: 150px; height: auto;" />
    </div>

    <h2>Добро пожаловать!</h2>

    <c:if test="${not empty successMessage}">
        <div class="success">✅ ${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="error">❌ ${errorMessage}</div>
    </c:if>

    <form action="loginServlet" method="post">
        <div>
            <label>Имя пользователя:</label>
            <input type="text" name="username" required />
        </div>
        <div>
            <label>Пароль:</label>
            <input type="password" name="password" required />
        </div>

        <button type="submit">Войти</button>
        <button type="button" onclick="location.href='register.jsp'">Регистрация</button>
    </form>
</div>
</body>
</html>
