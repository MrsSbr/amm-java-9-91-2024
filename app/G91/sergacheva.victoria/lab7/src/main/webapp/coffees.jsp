<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.vsu.amm.java.entity.Coffee" %>
<%@ page import="java.util.Set" %>

<html>
<head>
    <title>Список кофе</title>
</head>
<body>
<h1>Список кофе</h1>
<table border="2">
    <tr>
        <th>Название</th>
        <th>Описание</th>
        <th>Автор</th>
        <th>Действие</th>
    </tr>
    <%
        List<Coffee> coffeeList = (List<Coffee>) request.getAttribute("coffees");
        Set<Coffee> likedCoffees = (Set<Coffee>) request.getAttribute("likedCoffees");
        Long me_id = (Long) request.getAttribute("me_id");
        if (coffeeList != null && !coffeeList.isEmpty()) {
            for (Coffee coffee : coffeeList) {
    %>
    <tr>
        <td><%= coffee.getTitle() %></td>
        <td style="white-space: pre-line"><%= coffee.getDescription() %></td>
        <td><%= coffee.getAuthor().getName() %></td>
        <td><% if (me_id != null && me_id.equals(coffee.getAuthor().getId())) { %>
            <form action="/coffees/delete" method="get">
                <input type="hidden" name="id" value="<%= coffee.getId() %>">
                <button type="submit">Удалить</button>
            </form>
            <% } %>
            <% if (!likedCoffees.contains(coffee)) { %>
            <form action="/coffee-liked" method="post">
                <input type="hidden" name="id" value="<%= coffee.getId() %>">
                <button type="submit">Добавить в избранное</button>
            </form>
            <% } %>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="4">Список пуст</td>
    </tr>
    <%
        }
    %>
</table>
<a href="coffees/create">
    <button type="button" class="add-btn">Добавить</button>
</a>
<a href="/coffee-liked">Избранное</a>
<br>
<br>
<a href="/logout">Выйти</a>
</body>
</html>