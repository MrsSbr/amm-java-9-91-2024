<%--
  Created by IntelliJ IDEA.
  User: Максим Гаршин
  Date: 31.03.2025
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Notepad</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
</head>
<body>
<div class="container welcome">
    <div class="welcome-text">Welcome to Notepad</div>
    <div class="buttons">
        <form action="${pageContext.request.contextPath}/home" method="post">
            <input type="hidden" name="action" value="login">
            <button type="submit" class="btn login-button">Login</button>
        </form>
        <form action="${pageContext.request.contextPath}/home" method="post">
            <input type="hidden" name="action" value="register">
            <button type="submit" class="btn register-button">Register</button>
        </form>
    </div>
</div>
</body>
</html>