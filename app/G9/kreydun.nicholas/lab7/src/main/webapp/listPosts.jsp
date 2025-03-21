<%@ page import="ru.vsu.amm.java.entities.Post, ru.vsu.amm.java.entities.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Список постов</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 20px;
      background-color: #f4f4f4;
    }
    h1 {
      color: #333;
    }
    table {
      width: 100%;
      border-collapse: collapse;
      background: white;
    }
    th, td {
      border: 1px solid #ddd;
      padding: 10px;
      text-align: left;
    }
    th {
      background-color: #4CAF50;
      color: white;
    }
    .container {
      max-width: 800px;
      margin: auto;
      background: white;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
    }
    .btn {
      padding: 5px 10px;
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
    .btn-delete {
      background-color: red;
    }
    .btn-delete:hover {
      background-color: darkred;
    }
  </style>
</head>
<body>

<div class="container">
  <h1>Все посты</h1>

  <%
    User currentUser = (User) session.getAttribute("user");
    List<Post> postsList = (List<Post>) request.getAttribute("posts");
    if (postsList != null && !postsList.isEmpty()) {
  %>
  <table>
    <tr>
      <th>ID пользователя</th>
      <th>Содержание</th>
      <th>Действия</th>
    </tr>
    <% for (Post post : postsList) { %>
    <tr>
      <td><%= post.getUserId() %></td>
      <td><%= post.getContent().replace("\n", "<br>") %></td>
      <td>
        <% if (currentUser != null && currentUser.getId() == post.getUserId()) { %>
        <a href="updatePost.jsp?postId=<%= post.getId() %>" class="btn">Редактировать</a>
        <a href="deletePost?postId=<%= post.getId() %>" class="btn btn-delete" onclick="return confirm('Удалить пост?');">Удалить</a>
        <% } %>
      </td>
    </tr>
    <% } %>
  </table>
  <% } else { %>
  <p>Постов пока нет.</p>
  <% } %>

  <a href="index.jsp" class="btn">На главную</a>
</div>

</body>
</html>
