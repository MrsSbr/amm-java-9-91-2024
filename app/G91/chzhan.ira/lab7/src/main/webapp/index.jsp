<%@ page import="java.util.List" %>
<%@ page import="ru.vsu.amm.java.entities.Toy" %>
<%@ page import="ru.vsu.amm.java.entities.Customer" %>
<%@ page import="ru.vsu.amm.java.service.ToyService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Магазин игрушек</title>
    <style>
        .toy-card {
            border: 1px solid #ccc;
            padding: 10px;
            margin: 10px;
            width: 200px;
            display: inline-block;
            text-align: center;
        }
        .toy-card h3 {
            margin-top: 0;
        }
        .error-message {
            color: red;
        }
    </style>
</head>
<body>
<h1>Магазин игрушек</h1>

<%
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    ToyService toyService = new ToyService();
    List<Toy> toys = toyService.getAllToys();
    request.setAttribute("toys", toys);

    Customer customer = (Customer) session.getAttribute("customer");
    boolean isAdmin = false;
    if (customer != null && "admin".equals(customer.getName())) {
        isAdmin = true;
    }

    String errorMessage = (String) request.getAttribute("errorMessage");
%>

<% if (errorMessage != null) { %>
<p class="error-message"><%= errorMessage %></p>
<% } %>

<% if (isAdmin) { %>
<h2>Добавить игрушку</h2>
<form action="/addToy" method="post">
    Название: <input type="text" name="name"><br>
    Цена: <input type="number" step="0.01" name="price"><br>
    <button type="submit">Добавить</button>
</form>
<br>
<h2>Удалить игрушку</h2>
</form>
<br>
<% } %>

<div>
    <%
        List<Toy> toyList = (List<Toy>) request.getAttribute("toys");
        if (toyList != null) {
            for (Toy toy : toyList) {
    %>
    <div class="toy-card">
        <h3><%= toy.getName() %></h3>
        <p>Цена: <%= toy.getPrice() %> руб.</p>
        <form action="/order" method="post">
            <input type="hidden" name="toyId" value="<%= toy.getId() %>">
            <input type="hidden" name="price" value="<%= toy.getPrice() %>">
            Количество: <input type="number" name="quantity" value="1" min="1">
            <button type="submit">Купить</button>
        </form>
        <% if (isAdmin) { %>
        <form action="/deleteToy" method="post" style="display: inline;">
            <input type="hidden" name="toyId" value="<%= toy.getId() %>">
            <button type="submit">Удалить</button>
        </form>
        <% } %>
    </div>
    <%
            }
        }
    %>
</div>

<br>
<a href="/orders">История заказов</a>
<p>
    <%
        if (session.getAttribute("customer") == null) {
    %>
    <a href="/login">Войти</a> | <a href="/register">Регистрация</a>
    <%
    } else {
    %>
    <a href="/login">Выйти</a>
    <%
        }
    %>
</p>

</body>
</html>
