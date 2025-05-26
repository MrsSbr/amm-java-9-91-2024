<%@ page import="ru.vsu.amm.java.entity.RealEstate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Бронирование</title>
</head>
<body>
<div class="container">
    <h1>Бронирование недвижимости</h1>

    <% String errorMessage = (String) request.getAttribute("error"); %>
    <% if (errorMessage != null) { %>
    <div class="error-message"><%= errorMessage %></div>
    <% } %>

    <%
        RealEstate estate = (RealEstate) request.getAttribute("estate");
        if (estate != null) {
    %>
    <div class="info">
        <img src="/resources/images/<%=estate.getImageName()%>"
             class="estate-image">
        <h2>
            <% if("COTTAGE".equals(estate.getType().toString())) { %>
            Коттедж
            <% } else if ("APARTMENT".equals(estate.getType().toString())) { %>
            Квартира
            <% } else if ("HOUSE".equals(estate.getType().toString())) { %>
            Дом
            <% } %>
        </h2>
        <p><strong>Адрес:</strong> <%= estate.getAddress() %></p>
        <p><strong>Гостей:</strong> до <%= estate.getMaximumNumberOfGuests() %></p>
        <p><strong>Правила:</strong> <%= estate.getRules() %></p>
        <p>Цена: <%=estate.getPrice()%></p>
    </div>

    <form action="/booking" method="post">
        <input type="hidden" name="estateId" value="<%= estate.getId() %>">

        <div class="form-group">
            <label for="checkIn">Дата заезда:</label>
            <input type="date" id="checkIn" name="checkIn" required>
        </div>

        <div class="form-group">
            <label for="checkOut">Дата выезда:</label>
            <input type="date" id="checkOut" name="checkOut" required>
        </div>

        <div class="form-group">
            <label for="guests">Количество гостей:</label>
            <input type="number" id="guests" name="guests"
                   min="1" max="<%= estate.getMaximumNumberOfGuests() %>" required>
        </div>

        <button type="submit" class="submit-btn">Подтвердить бронирование</button>
    </form>
    <% } else { %>
    <p>Недвижимость не найдена.</p>
    <% } %>
    <a href="/home">Вернуться к поиску</a>
</div>
</body>
</html>