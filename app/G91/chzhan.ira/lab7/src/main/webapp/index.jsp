<%@ page import="java.util.List" %>
<%@ page import="ru.vsu.amm.java.entities.Toy" %>
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
    </style>
</head>
<body>
<h1>Магазин игрушек</h1>

<%
    ToyService toyService = new ToyService();
    List<Toy> toys = toyService.getAllToys();
    request.setAttribute("toys", toys);
%>

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
    </div>
    <%
            }
        }
    %>
</div>

<h2>Добавить игрушку</h2>
<form action="/addToy" method="post">
    Название: <input type="text" name="name"><br>
    Цена: <input type="number" name="price"><br>
    <button type="submit">Добавить</button>
</form>

<br>
<a href="/orders">История заказов</a>

</body>
</html>
