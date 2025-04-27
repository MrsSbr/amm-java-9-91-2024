<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Мои сессии</title>
  <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
  <style>
    :root {
      --gradient-start: #8e2de2;
      --gradient-end:   #4a00e0;
      --bg-card:        #ffffff;
      --text-dark:      #333333;
      --text-light:     #f5f5f5;
      --hover-light:    rgba(255,255,255,0.2);
    }
    * { box-sizing: border-box; margin: 0; padding: 0; }
    body {
      background: #f2f2f7;
      font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
      color: var(--text-dark);
      line-height: 1.4;
    }
    .header {
      background: linear-gradient(135deg, var(--gradient-start), var(--gradient-end));
      padding: 1.5rem 2rem;
      display: flex;
      justify-content: space-between;
      align-items: center;
      border-radius: 0 0 1rem 1rem;
    }
    .header h1 {
      color: var(--text-light);
      font-size: 1.75rem;
      margin: 0;
    }
    .header .btn {
      background: var(--bg-card);
      color: var(--gradient-end);
      padding: 0.6rem 1.2rem;
      text-decoration: none;
      border-radius: 0.5rem;
      font-weight: bold;
      transition: background 0.3s;
    }
    .header .btn:hover {
      background: var(--hover-light);
    }
    .container {
      max-width: 900px;
      margin: -1rem auto 2rem;
      background: var(--bg-card);
      padding: 2rem;
      border-radius: 1rem;
      box-shadow: 0 4px 16px rgba(0,0,0,0.1);
    }
    table {
      width: 100%;
      border-collapse: collapse;
      margin-bottom: 1.5rem;
    }
    th, td {
      padding: 0.75rem 1rem;
      text-align: left;
    }
    th {
      background: linear-gradient(135deg, var(--gradient-start), var(--gradient-end));
      color: var(--text-light);
      font-weight: normal;
    }
    tr {
      background: #f9f9fb;
    }
    tr + tr td {
      border-top: 1px solid #ececec;
    }
    tr:hover {
      background: #ece8f9;
    }
  </style>
</head>
<body>
<div class="header">
  <h1>Мои сессии</h1>
  <a href="${pageContext.request.contextPath}/client/login" class="btn">
    Войти как пациент
  </a>
</div>

<div class="container">
  <table>
    <thead>
    <tr>
      <th>ID</th>
      <th>Дата</th>
      <th>Цена</th>
      <th>Длительность (мин)</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="s" items="${sessions}">
      <tr>
        <td>${s.idSession}</td>
        <td>${s.date}</td>
        <td>${s.price}</td>
        <td>${s.duration}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>
</body>
</html>
