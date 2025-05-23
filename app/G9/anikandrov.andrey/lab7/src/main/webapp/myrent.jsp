<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Мои брони</title>
  <style>
    body {
      font-family: Arial;
      padding: 20px;
    }

    h1 {
      color: #4682b4;
    }

    section {
      margin-bottom: 20px;
    }

    div.booking {
      margin-bottom: 15px;
      padding: 10px;
      border: 1px solid #ccc;
    }

    button {
      background: #4682b4;
      color: white;
      border: none;
      padding: 5px 10px;
    }

    .cancel {
      background: #f44336;
    }

    .no-bookings {
      font-style: italic;
      color: #666;
    }
  </style>
</head>
<body>
<h1>Мои бронирования</h1>
<a href="/home">
  <button>На главную</button>
</a>

<section>
  <h2>Предметы</h2>
  <c:set var="hasItems" value="false" />
  <c:forEach items="${agreements}" var="agreement" varStatus="loop">
    <c:if test="${not empty rentalObjects[loop.index] and rentalObjects[loop.index].objectType eq 'ITEM'}">
      <c:set var="hasItems" value="true" />
      <div class="booking">
        <h3>Договор №${agreement.agreementID}</h3>
        <p>С ${agreement.timeStart} по ${agreement.timeEnd}</p>
        <p>Цена: ${agreement.sumPrice} руб.</p>
        <p><b>${rentalObjects[loop.index].objectName}</b></p>
        <p>Тип: ${rentalObjects[loop.index].objectType}</p>
        <form action="cancel" method="post">
          <input type="hidden" name="agreementId" value="${agreement.agreementID}">
          <button type="submit" class="cancel">Отменить</button>
        </form>
      </div>
    </c:if>
  </c:forEach>
  <c:if test="${not hasItems}">
    <p class="no-bookings">Нет бронирований предметов</p>
  </c:if>
</section>

<section>
  <h2>Проживание</h2>
  <c:set var="hasHabitations" value="false" />
  <c:forEach items="${agreements}" var="agreement" varStatus="loop">
    <c:if test="${not empty rentalObjects[loop.index] and rentalObjects[loop.index].objectType eq 'HABITATION'}">
      <c:set var="hasHabitations" value="true" />
      <div class="booking">
        <h3>Договор №${agreement.agreementID}</h3>
        <p>С ${agreement.timeStart} по ${agreement.timeEnd}</p>
        <p>Цена: ${agreement.sumPrice} руб.</p>
        <p><b>${rentalObjects[loop.index].objectName}</b></p>
        <p>Тип: ${rentalObjects[loop.index].objectType}</p>
        <form action="cancel" method="post">
          <input type="hidden" name="agreementId" value="${agreement.agreementID}">
          <button type="submit" class="cancel">Отменить</button>
        </form>
      </div>
    </c:if>
  </c:forEach>
  <c:if test="${not hasHabitations}">
    <p class="no-bookings">Нет бронирований проживания</p>
  </c:if>
</section>

<section>
  <h2>Мероприятия</h2>
  <c:set var="hasEvents" value="false" />
  <c:forEach items="${agreements}" var="agreement" varStatus="loop">
    <c:if test="${not empty rentalObjects[loop.index] and rentalObjects[loop.index].objectType eq 'EVENT'}">
      <c:set var="hasEvents" value="true" />
      <div class="booking">
        <h3>Договор №${agreement.agreementID}</h3>
        <p>С ${agreement.timeStart} по ${agreement.timeEnd}</p>
        <p>Цена: ${agreement.sumPrice} руб.</p>
        <p><b>${rentalObjects[loop.index].objectName}</b></p>
        <p>Тип: ${rentalObjects[loop.index].objectType}</p>
        <form action="cancel" method="post">
          <input type="hidden" name="agreementId" value="${agreement.agreementID}">
          <button type="submit" class="cancel">Отменить</button>
        </form>
      </div>
    </c:if>
  </c:forEach>
  <c:if test="${not hasEvents}">
    <p class="no-bookings">Нет бронирований мероприятий</p>
  </c:if>
</section>
</body>
</html>