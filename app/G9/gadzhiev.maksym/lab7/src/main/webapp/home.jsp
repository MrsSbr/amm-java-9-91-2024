<%@ page import="ru.vsu.amm.java.entity.RealEstate" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Доступная недвижимость</h1>

<%
    List<RealEstate> realEstates = (List<RealEstate>) request.getAttribute("realEstates");
    Boolean isAuth = (Boolean) request.getAttribute("isAuth");
    if (realEstates != null) {
        for (RealEstate estate : realEstates) {
%>
<div class="row">
    <div class="col">
        <div class="estate">
            <img src="/resources/images/<%=estate.getImageName()%>"
                 class="estate-image">
            <h3>
                <% if("COTTAGE".equals(estate.getType().toString())) { %>
                Коттедж
                <% } else if ("APARTMENT".equals(estate.getType().toString())) { %>
                Квартира
                <% } else if ("HOUSE".equals(estate.getType().toString())) { %>
                Дом
                <% } %>
            </h3>
            <p>Адрес: <%=estate.getAddress()%></p>
            <p>Гостей: <%=estate.getMaximumNumberOfGuests()%></p>
            <p>Правила: <%=estate.getRules()%></p>
            <p>Цена: <%=estate.getPrice()%></p>

            <% if (isAuth) { %>
            <form action="/booking" method="get">
                <input type="hidden" name="estateId" value="<%=estate.getId()%>">
                <button type="submit" class="book-btn">Забронировать</button>
            </form>
            <% } else { %>
            <p><a href="/login">Войдите</a> для бронирования</p>
            <% } }}%>
        </div>
    </div>
</div>
</body>
</html>