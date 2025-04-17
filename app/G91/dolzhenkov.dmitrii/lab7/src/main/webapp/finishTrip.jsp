<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.vsu.amm.java.dto.TripEndDto" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.Locale" %>

<html>
<head>
  <title>Завершение поездки</title>
</head>
<body>
<h2>Ваши незавершённые поездки</h2>

<%
  List<TripEndDto> trips = (List<TripEndDto>) request.getAttribute("trips");
  if (trips != null && !trips.isEmpty()) {
    for (TripEndDto trip : trips) {
%>
<%
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm", new Locale("ru"));
%>
<div style="border: 1px solid #ccc; padding: 10px; margin-bottom: 10px;">
  <p><strong>Самокат ID:</strong> <%= trip.scooterId() %></p>
  <p><strong>Модель самоката:</strong> <%= trip.model() %></p>
  <p><strong>Дата начала:</strong> <%= trip.startTime().format(formatter) %></p>
  <form method="post" action="finishTrip">
    <input type="hidden" name="tripId" value="<%= trip.tripId() %>"/>
    <label>Широта:
      <input type="text" name="latitude" required/>
    </label>
    <br/>
    <label>Долгота:
      <input type="text" name="longitude" required/>
    </label>
    <br/>
    <button type="submit">Завершить поездку</button>
  </form>
</div>
<%
  }
} else {
%>
<p>Нет активных поездок.</p>
<%
  }
%>

<!-- Кнопка "Назад на главную" -->
<form action="main.jsp" method="get">
  <button type="submit">Назад на главную</button>
</form>

</body>
</html>
