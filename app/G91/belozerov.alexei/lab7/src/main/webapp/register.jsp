<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Регистрация</title>
  <style>
    body {font-family: Arial, sans-serif; background:#f5f5f5;}
    .container {width:400px;margin:100px auto;background:white;padding:20px;border-radius:8px;box-shadow:0 0 10px rgba(0,0,0,.1);}
    input[type=text],input[type=password]{width:100%;padding:10px;margin:5px 0 15px 0;border:1px solid #ccc;border-radius:4px;}
    input[type=submit]{background:#28a745;color:#fff;border:none;padding:10px 20px;border-radius:4px;cursor:pointer;}
    input[type=submit]:hover{background:#218838;}
    .error{color:red;}
  </style>
</head>
<body>
<div class="container">
  <h2>Регистрация</h2>
  <c:if test="${not empty errorMessage}">
    <p class="error"><c:out value="${errorMessage}"/></p>
  </c:if>
  <form method="post" action="${pageContext.request.contextPath}/register">
    <label for="name">Имя</label>
    <input type="text" id="name" name="name" required/>
    <label for="email">Email</label>
    <input type="text" id="email" name="email" required/>
    <label for="password">Пароль</label>
    <input type="password" id="password" name="password" required/>
    <input type="submit" value="Зарегистрироваться"/>
  </form>
  <p>Уже есть аккаунт? <a href="${pageContext.request.contextPath}/login">Войти</a></p>
</div>
</body>
</html>