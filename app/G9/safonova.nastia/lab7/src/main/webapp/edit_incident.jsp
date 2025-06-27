<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<html>
<head>
  <title>Редактировать инцидент</title>
  <style>
    body { background-color: #f5f5dc; font-family: Arial; }
    .container { max-width: 600px; margin: 20px auto; padding: 20px; }
    .form-group { margin-bottom: 15px; }
    label { display: block; margin-bottom: 5px; color: #556b2f; }
    input, select, textarea {
      width: 100%;
      padding: 8px;
      border: 1px solid #ccc;
      border-radius: 4px;
      box-sizing: border-box;
    }
    .btn {
      background: #8fbc8f;
      color: white;
      padding: 10px 20px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }
    .btn-cancel {
      background: #d2b48c;
      margin-left: 10px;
    }
  </style>
</head>
<body>
<div class="container">
  <h1>Редактировать инцидент</h1>
  <form action="update-incident" method="post">

    <input type="hidden" name="id" value="${incident.idIncident}">

    <div class="form-group">
      <label>Дата:</label>
      <input type="date" name="date" value="${incident.dateOfIncident}" required>
    </div>

    <div class="form-group">
      <label>Описание:</label>
      <textarea name="description" rows="4" required>${incident.description}</textarea>
    </div>

    <button type="submit" class="btn">Сохранить</button>
    <a href="incidents" class="btn btn-cancel">Отмена</a>

  </form>
</div>
</body>
</html>