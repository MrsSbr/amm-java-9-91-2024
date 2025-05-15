<%@ page import="java.util.List" %>
<%@ page import="ru.vsu.amm.java.entities.Order" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>История заказов</title>
</head>
<body>
<h1>История заказов</h1>

<jsp:useBean id="orders" scope="request" type="java.util.List"/>

<% if (orders == null || orders.isEmpty()) { %>
<p>У вас пока нет заказов.</p>
<% } else { %>
<table>
    <thead>
    <tr>
        <th>ID Заказа</th>
        <th>ID Игрушки</th>
        <th>Количество</th>
        <th>Сумма</th>
    </tr>
    </thead>
    <tbody>
    <% for (Order order : (List<Order>) orders) { %>
    <tr>
        <td><%= order.getId() %></td>
        <td><%= order.getToyId() %></td>
        <td><%= order.getQuantity() %></td>
        <td><%= order.getTotalPrice() %></td>
    </tr>
    <% } %>
    </tbody>
</table>
<% } %>

<br>
<a href="index.jsp">Вернуться в магазин</a>
</body>
</html>
