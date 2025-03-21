<%@ page import="ru.vsu.amm.java.entities.User" %>
<%@ page import="ru.vsu.amm.java.entities.Post" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Главная страница</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        .container { max-width: 800px; margin: auto; background: white; padding: 20px; border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); text-align: center; }
        h1 { color: #333; }
        .btn { display: inline-block; margin-top: 10px; padding: 10px 15px; background-color: #4CAF50;
            color: white; text-decoration: none; border-radius: 5px; }
        .btn:hover { background-color: #45a049; }
        .btn-danger { background-color: red; }
        .btn-danger:hover { background-color: darkred; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; background: white; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #4CAF50; color: white; }
        .actions { text-align: center; }
        .message { padding: 10px; margin-bottom: 10px; border-radius: 5px; }
        .success { background-color: #d4edda; color: green; border: 1px solid #c3e6cb; }
        .error { background-color: #f8d7da; color: red; border: 1px solid #f5c6cb; }
    </style>
</head>
<body>

<%
    User user = (User) session.getAttribute("user");
    List<Post> posts = (List<Post>) request.getAttribute("posts");
    String successMessage = (String) session.getAttribute("success");
    String errorMessage = (String) session.getAttribute("error");
    session.removeAttribute("success");
    session.removeAttribute("error");
%>

<div class="container">
    <h1>Главная страница</h1>

    <% if (successMessage != null) { %>
    <div class="message success"><%= successMessage %></div>
    <% } %>
    <% if (errorMessage != null) { %>
    <div class="message error"><%= errorMessage %></div>
    <% } %>

    <% if (user != null) { %>
    <p>Добро пожаловать, <strong><%= user.getUsername() %></strong>!</p>

    <h2>Управление постами</h2>
    <a href="createPost.jsp" class="btn">Создать пост</a>

    <% if (posts != null && !posts.isEmpty()) { %>
    <h2>Ваши посты</h2>
    <table>
        <tr>
            <th>ID</th>
            <th>Содержание</th>
            <th class="actions">Действия</th>
        </tr>
        <% for (Post post : posts) { %>
        <tr>
            <td><%= post.getId() %></td>
            <td><%= post.getContent().replace("\n", "<br>") %></td>
            <td class="actions">
                <a href="updatePost.jsp?postId=<%= post.getId() %>" class="btn">Редактировать</a>
                <form action="deletePost" method="post" style="display:inline;">
                    <input type="hidden" name="postId" value="<%= post.getId() %>">
                    <button type="submit" class="btn btn-danger"
                            onclick="return confirm('Вы уверены, что хотите удалить этот пост?');">
                        Удалить
                    </button>
                </form>
            </td>
        </tr>
        <% } %>
    </table>
    <% } else { %>
    <p>У вас пока нет постов.</p>
    <% } %>

    <h2>Управление аккаунтом</h2>
    <a href="dashboard.jsp" class="btn">Личный кабинет</a>

    <% } else { %>
    <p>Вы не авторизованы.</p>
    <a href="login.jsp" class="btn">Войти</a>
    <a href="register.jsp" class="btn">Регистрация</a>
    <% } %>
</div>

</body>
</html>
