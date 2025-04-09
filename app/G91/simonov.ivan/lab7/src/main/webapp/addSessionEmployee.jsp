<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Добавление сессии</title>
  <style>
    .form-group {
      margin-bottom: 15px;
    }
    label {
      display: inline-block;
      width: 200px;
    }
    input[title]:hover:after {
      content: attr(title);
      position: absolute;
      background: #ffffcc;
      border: 1px solid #cccccc;
      padding: 5px;
      z-index: 100;
    }
  </style>
</head>
<body>

<h1>Добавление сессии</h1>

<c:if test="${not empty param.error}">
  <p style="color: red;">${param.error}</p>
</c:if>
<c:if test="${not empty param.message}">
  <p style="color: #51d251;">${param.message}</p>
</c:if>

<form action="addSession" method="post">

  <div class="form-group">
    <label for="userId">ID пользователя:</label>
    <input type="text" id="userId" name="userId" required>
  </div>

  <div class="form-group">
    <label for="registrationNumber">Регистрационный номер:</label>
    <input type="text" id="registrationNumber" name="registrationNumber"
           title="Формат: БУКВЫ:ЦИФРЫ:РЕГИОН:СТРАНА"
           required>
  </div>

  <div class="form-group">
    <label for="model">Модель:</label>
    <input type="text" id="model" name="model" required>
  </div>

  <div class="form-group">
    <label for="brand">Бренд:</label>
    <input type="text" id="brand" name="brand" required>
  </div>

  <div class="form-group">
    <label for="colour">Цвет:</label>
    <input type="text" id="colour" name="colour" required>
  </div>

  <div class="form-group">
    <label for="parkingPrice">Цена парковки:</label>
    <input type="text" id="parkingPrice" name="parkingPrice"
           pattern="^\d+(\.\d{1,2})?$"
           title="Введите число с двумя десятичными знаками (например: 10.50)"
           required>
  </div>

  <button type="submit">Добавить</button>
</form>
<p><a href="employeeActions.jsp" class="return-link">Перейти к списку действий</a></p>
</body>
</html>
