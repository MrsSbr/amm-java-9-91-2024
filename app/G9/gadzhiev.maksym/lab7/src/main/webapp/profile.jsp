<%@ page import="ru.vsu.amm.java.entity.Booking" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="container">
    <h1>Мой профиль</h1>
    <% String cancelSuccess = request.getParameter("cancelSuccess");
       if (cancelSuccess != null) { %>
    <div>Бронирование успешно отменено</div>
    <% } %>
    <h2>Мои бронирования</h2>
    <% List<Booking> bookings = (List<Booking>) request.getAttribute("bookings");
    if (bookings != null && !bookings.isEmpty()) {
        for (Booking booking : bookings) {
    %>
    <div class="booking-card">
        <h3>Бронирование номер <%=booking.getId()%></h3>
        <p>Статус:<%=booking.getStatus()%></p>
        <p>Дата заезда: <%=booking.getCheckInDate()%></p>
        <p>Дата выезда: <%=booking.getCheckOutDate()%></p>
        <form action="profile" method="post">
            <input type="hidden" name="action" value="cancel">
            <input type="hidden" name="bookingId" value="<%= booking.getId()%>">
            <button type="submit" class="btn-cancel">Отменить бронирование</button>
        </form>
    </div>
    <% }
    } else { %> <p>У вас пока нет забронированной недвижимости</p>
    <%}%>
    <p><a href="home">Вернуться к просмотру объявлений</a></p>
</div>
</body>
</html>
