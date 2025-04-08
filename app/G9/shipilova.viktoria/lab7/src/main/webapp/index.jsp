<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Главная страница</title>
</head>
<body>
<h1>Добро пожаловать в приложение бюро находок!</h1>
<p>Вы можете:</p>
<ul>
    <li><a href="listProperties">Просмотреть список найденных вещей</a></li>
    <li><a href="authorization?redirectTo=listProperties%3Feditable%3Dtrue">Просмотреть список найденных вещей с возможностью забрать вещь</a></li>
    <li><a href="authorization?redirectTo=addProperty.jsp">Добавить вещь</a></li>
    <li><a href="login.jsp">Войти</a> или <a href="register.jsp">Зарегистрироваться</a>, чтобы добавить или забрать вещь</li>
</ul>
</body>
</html>