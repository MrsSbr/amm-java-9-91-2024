<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Детали сессии</title>
    <style>
        :root {
            --gradient-start: #8e2de2;
            --gradient-end:   #4a00e0;
            --bg-card:        #ffffff;
            --text-dark:      #333333;
            --text-light:     #f5f5f5;
            --border-color:   #ddd;
        }
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body {
            background: #f2f2f7;
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            color: var(--text-dark);
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
        }
        .card {
            width: 360px;
            background: var(--bg-card);
            border-radius: 1rem;
            box-shadow: 0 4px 16px rgba(0,0,0,0.1);
            overflow: hidden;
        }
        .card-header {
            background: linear-gradient(135deg, var(--gradient-start), var(--gradient-end));
            padding: 1.5rem;
            text-align: center;
        }
        .card-header h2 {
            color: var(--text-light);
            font-size: 1.6rem;
        }
        .card-body {
            padding: 1.5rem;
        }
        .card-body p {
            margin-bottom: 0.75rem;
            font-size: 0.95rem;
        }
        .card-body p strong {
            color: var(--gradient-start);
        }
        .btn {
            display: block;
            width: 100%;
            padding: 0.75rem;
            margin-top: 1rem;
            border: none;
            border-radius: 0.5rem;
            background: linear-gradient(135deg, var(--gradient-start), var(--gradient-end));
            color: var(--text-light);
            font-size: 1rem;
            font-weight: bold;
            text-align: center;
            cursor: pointer;
            text-decoration: none;
            transition: opacity 0.3s;
        }
        .btn:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>
<div class="card">
    <div class="card-header">
        <h2>Детали сессии</h2>
    </div>
    <div class="card-body">
        <p><strong>Психолог:</strong> ${psychologistName}</p>
        <p><strong>Клиент:</strong> ${clientName} (${clientEmail})</p>
        <p><strong>Email клиента:</strong> ${clientEmail}</p>
        <p><strong>Дата:</strong> ${session.date}</p>
        <p><strong>Длительность:</strong> ${session.duration} мин.</p>
        <p><strong>Стоимость:</strong> ${session.price}</p>
        <a href="${pageContext.request.contextPath}/client/sessions" class="btn">
            Назад к списку
        </a>
    </div>
</div>
</body>
</html>
