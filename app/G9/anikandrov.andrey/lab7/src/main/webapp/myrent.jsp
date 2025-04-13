<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Мои брони</title>
  <style>
    body { font-family: Arial; padding: 20px; }
    h1 { color: #4682b4; }
    div { margin-bottom: 15px; padding: 10px; border: 1px solid #ccc; }
    button { background: #4682b4; color: white; border: none; padding: 5px 10px; }
    .cancel { background: #f44336; }
  </style>
</head>
<body>
<h1>Мои бронирования</h1>
<a href="/home"><button>На главную</button></a>

<c:forEach items="${agreements}" var="agreement" varStatus="loop">
  <div>
    <h3>Договор №${agreement.agreementID}</h3>
    <p>С ${agreement.timeStart} по ${agreement.timeEnd}</p>
    <p>Цена: ${agreement.sumPrice} руб.</p>

    <c:if test="${not empty rentalObjects[loop.index]}">
      <p><b>${rentalObjects[loop.index].objectName}</b></p>
      <p>Тип: ${rentalObjects[loop.index].objectType}</p>
    </c:if>

    <form action="cancel" method="post">
      <input type="hidden" name="agreementId" value="${agreement.agreementID}">
      <button type="submit" class="cancel">Отменить</button>
    </form>
  </div>
</c:forEach>

<c:if test="${empty agreements}">
  <p>У вас нет бронирований</p>
</c:if>
</body>
</html>