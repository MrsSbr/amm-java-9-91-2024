<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Подтверждение бронирования</title>
  <style>
    body {font-family: Arial, sans-serif;background:#f5f5f5;}
    .container{width:500px;margin:40px auto;background:white;padding:20px;border-radius:8px;box-shadow:0 0 10px rgba(0,0,0,.1);}
    .details{margin-bottom:20px;}
    .details span{display:block;margin:5px 0;}
    input[type=submit]{background:#007bff;color:#fff;border:none;padding:10px 20px;border-radius:4px;cursor:pointer;}
    input[type=submit]:hover{background:#0056b3;}
  </style>
</head>
<body>
<div class="container">
  <h2>Подтверждение бронирования</h2>
  <div class="details">
    <span>Рейс ID: <%= session.getAttribute("flightId") %></span>
    <span>Место: <%= session.getAttribute("seatNumber") %></span>
    <span>Номер билета: <%= session.getAttribute("ticketNumber") %></span>
  </div>
  <form method="post" action="${pageContext.request.contextPath}/book">
    <input type="submit" value="Подтвердить"/>
  </form>
  <p><a href="${pageContext.request.contextPath}/available-bookings">Назад к выбору места</a></p>
</div>
</body>
</html>