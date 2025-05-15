<%@ page import="ru.vsu.amm.java.entities.Post" %>
<%@ page import="ru.vsu.amm.java.repository.PostRepository" %>
<%@ page import="java.util.UUID" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String postIdParam = request.getParameter("postId");
    Post post = null;

    if (postIdParam != null) {
        UUID postId = UUID.fromString(postIdParam);
        PostRepository postRepository = new PostRepository();
        post = postRepository.getById(postId);
    }
%>

<html>
<head>
    <title>Редактировать пост</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        .container { max-width: 600px; margin: auto; background: white; padding: 20px; border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); }
        h1 { color: #333; }
        textarea { width: 100%; height: 100px; padding: 10px; border: 1px solid #ddd; border-radius: 5px; resize: vertical; }
        .btn { display: block; width: 100%; margin-top: 10px; padding: 10px; background-color: #4CAF50;
            color: white; text-decoration: none; border: none; border-radius: 5px; text-align: center; cursor: pointer; }
        .btn:hover { background-color: #45a049; }
    </style>
</head>
<body>

<div class="container">
    <h1>Редактировать пост</h1>

    <% if (post != null) { %>
    <form action="updatePost" method="post">
        <input type="hidden" name="postId" value="<%= post.getId() %>">
        <label>Содержание поста:</label>
        <textarea name="content" required><%= post.getContent() %></textarea>
        <button type="submit" class="btn">Сохранить изменения</button>
    </form>
    <% } else { %>
    <p style="color: red;">Ошибка: Пост не найден.</p>
    <% } %>

    <a href="index" class="btn">Назад</a>
</div>

</body>
</html>
