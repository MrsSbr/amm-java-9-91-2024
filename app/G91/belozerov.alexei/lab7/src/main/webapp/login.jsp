<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Вход</title>
    <style>
        body {font-family: Arial, sans-serif; background:#f5f5f5; }
        .container {width:400px;margin:100px auto;background:white;padding:20px;border-radius:8px;box-shadow:0 0 10px rgba(0,0,0,.1);}
        input[type=text],input[type=password]{width:100%;padding:10px;margin:5px 0 15px 0;border:1px solid #ccc;border-radius:4px;}
        input[type=submit]{background:#007bff;color:#fff;border:none;padding:10px 20px;border-radius:4px;cursor:pointer;}
        input[type=submit]:hover{background:#0056b3;}
        .error{color:red;}
    </style>
</head>
<body>
<div class="container">
    <h2>Вход</h2>
    <c:if test="${not empty errorMessage}">
        <p class="error"><c:out value="${errorMessage}"/></p>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <label for="email">Email</label>
        <input type="text" id="email" name="email" required/>
        <label for="password">Пароль</label>
        <input type="password" id="password" name="password" required/>
        <input type="submit" value="Войти"/>
    </form>
    <p>Нет аккаунта? <a href="${pageContext.request.contextPath}/register">Зарегистрироваться</a></p>
</div>
</body>
</html>
