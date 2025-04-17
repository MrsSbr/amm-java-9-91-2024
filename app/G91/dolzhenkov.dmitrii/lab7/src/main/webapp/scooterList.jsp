<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.vsu.amm.java.dto.ScooterDto" %>

<html>
<head>
  <title>Список самокатов</title>
</head>
<body>
<h2>Доступные самокаты</h2>

<%
  List<ScooterDto> scooters = (List<ScooterDto>) request.getAttribute("scooters");
  if (scooters != null && !scooters.isEmpty()) {
    for (ScooterDto scooter : scooters) {
%>
<div style="border:1px solid #ccc; padding:10px; margin-bottom:10px;">
  <p><strong>Модель:</strong> <%= scooter.model() %></p>
  <p><strong>Координаты:</strong> <%= scooter.latitude() %>, <%= scooter.longitude() %></p>
  <form action="scooters" method="post">
    <input type="hidden" name="scooterId" value="<%= scooter.id() %>"/>
    <button type="submit">Забронировать</button>
  </form>
</div>
<%
  }
} else {
%>
<p>Нет доступных самокатов.</p>
<%
  }
%>

<!-- Кнопка "Назад на главную" -->
<form action="main.jsp" method="get">
  <button type="submit">Назад на главную</button>
</form>

</body>
</html>
