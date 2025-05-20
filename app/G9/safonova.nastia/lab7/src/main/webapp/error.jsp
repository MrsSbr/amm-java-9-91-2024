<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Ошибка</title>
  <style>
    body {
      background-color: #f5f5dc;
      font-family: Arial;
      text-align: center;
      padding: 50px;
    }
    .error-message {
      color: red;
      font-size: 18px;
    }
  </style>
</head>
<body>
<h1>Произошла ошибка</h1>
<div class="error-message">${error}</div>
<a href="mainPage.jsp">Вернуться на главную</a>
</body>
</html>