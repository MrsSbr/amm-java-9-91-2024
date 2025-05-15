<%@ page import="ru.vsu.amm.java.entities.Post" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Post> posts = (List<Post>) request.getAttribute("posts");
%>

<h2>Ваши посты</h2>

<% if (posts != null && !posts.isEmpty()) { %>
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
            <a href="deletePost?postId=<%= post.getId() %>" class="btn btn-danger"
               onclick="return confirm('Вы уверены, что хотите удалить этот пост?');">Удалить</a>
        </td>
    </tr>
    <% } %>
</table>
<% } else { %>
<p>У вас пока нет постов.</p>
<% } %>
