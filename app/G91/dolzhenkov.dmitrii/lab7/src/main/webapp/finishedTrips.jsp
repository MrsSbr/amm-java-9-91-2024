<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.vsu.amm.java.dto.TripDto" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
  List<TripDto> trips = (List<TripDto>) request.getAttribute("trips");
%>

<html>
<head>
  <title>Завершённые поездки</title>
</head>
<body>
<h2>Завершённые поездки</h2>

<%
  if (trips != null && !trips.isEmpty()) {
    for (TripDto trip : trips) {
%>
<div style="border: 1px solid #ccc; padding: 10px; margin-bottom: 10px;">
  <p><strong>Самокат:</strong> <%= trip.model() %> (ID: <%= trip.scooterId() %>)</p>
  <p><strong>Начало:</strong> <%= trip.startTime().format(formatter) %></p>
  <p><strong>Конец:</strong> <%= trip.endTime().format(formatter) %></p>
  <p><strong>Дистанция:</strong> <%= trip.distance() != null ? String.format("%.2f км", trip.distance()) : "неизвестна" %></p>
</div>
<%
  }
} else {
%>
<p>Нет завершённых поездок.</p>
<%
  }
%>

<br>
<form action="main.jsp" method="get">
  <button type="submit">Назад</button>
</form>

</body>
</html>
