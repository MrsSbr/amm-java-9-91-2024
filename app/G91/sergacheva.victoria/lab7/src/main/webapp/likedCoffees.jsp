<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.vsu.amm.java.entity.Coffee" %>

<html>
<head>
    <title>Избранное</title>
</head>
<body>
<h1>Избранное</h1>
<table border="2">
    <tr>
        <th>Название</th>
        <th>Описание</th>
        <th>Автор</th>
        <th>Действие</th>
    </tr>
    <%
        List<Coffee> coffeeList = (List<Coffee>) request.getAttribute("coffees");
        if (coffeeList != null && !coffeeList.isEmpty()) {
            for (Coffee coffee : coffeeList) {
    %>
    <tr>
        <td><%= coffee.getTitle() %></td>
        <td style="white-space: pre-line"><%= coffee.getDescription() %></td>
        <td><%= coffee.getAuthor().getName() %></td>
        <td>
            <form action="/coffee-liked/delete" method="get">
                <input type="hidden" name="id" value="<%= coffee.getId() %>">
                <button type="submit">Удалить из избранного</button>
            </form>
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
<a href="/coffees">Весь список</a>
<br>
<br>
<a href="/logout">Выйти</a>
</body>
</html>