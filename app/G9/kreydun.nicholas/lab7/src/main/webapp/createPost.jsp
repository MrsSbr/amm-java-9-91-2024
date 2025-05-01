<%@ page import="ru.vsu.amm.java.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Создать пост</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 600px;
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #333;
        }
        textarea {
            width: 100%;
            height: 100px;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 5px;
            resize: none;
        }
        .btn {
            display: inline-block;
            margin-top: 10px;
            padding: 10px 15px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            border: none;
            cursor: pointer;
        }
        .btn:hover {
            background-color: #45a049;
        }
        .error {
            color: red;
        }
        .success {
            color: green;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Создать новый пост</h1>

    <%-- Проверяем, авторизован ли пользователь --%>
    <%
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
    %>

    <%-- Сообщения об успехе/ошибке --%>
    <%
        String success = (String) session.getAttribute("success");
        String error = (String) session.getAttribute("error");
        if (success != null) {
    %>
    <p class="success"><%= success %></p>
    <%
            session.removeAttribute("success");
        }
        if (error != null) {
    %>
    <p class="error"><%= error %></p>
    <%
            session.removeAttribute("error");
        }
    %>

    <%-- Форма создания поста --%>
    <form action="createPost" method="post">
        <input type="hidden" name="userId" value="<%= user.getId() %>">

        <label>Содержание поста:</label>
        <textarea name="content" required></textarea>

        <button type="submit" class="btn">Опубликовать</button>
    </form>

    <a href="index" class="btn">На главную</a>
</div>

</body>
</html>
