<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Все инциденты</title>
    <style>
        body { background-color: #f5f5dc; font-family: Arial; padding: 20px; }
        .container { max-width: 800px; margin: 0 auto; }
        .incident-card {
            background: #e9ffd9;
            padding: 15px;
            margin: 10px 0;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .action-buttons {
            margin-top: 10px;
            display: flex;
            gap: 10px;
        }
        .btn-edit {
            background: #8fbc8f;
        }
        .btn-delete {
            background: #ff6666;
        }
        .btn-cancel {
            background: #d2b48c;
            margin-left: 10px;
        }
        .btn {
            padding: 8px 16px;
            border: none;
            border-radius: 20px;
            cursor: pointer;
            color: white;
            text-decoration: none;
            font-size: 14px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Список инцидентов</h1>
        <button type="button" onclick="location.href='mainPage.jsp'" class="btn btn-cancel">На главную</button>
    </div>

    <c:forEach items="${incidents}" var="incident">
        <div class="incident-card">
            <h3>Инцидент #${incident.idIncident}</h3>
            <p>Дата: ${incident.dateOfIncident}</p>
            <p>Описание: ${incident.description}</p>
            <p>Сотрудник: ${incident.emplId}</p>
            <p>Динозавр: ${incident.dinoId}</p>
            <div class="action-buttons">
                <a href="edit-incident?id=${incident.idIncident}" class="btn btn-edit">Изменить</a>
                <form action="delete-incident" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="${incident.idIncident}">
                    <button type="submit" class="btn btn-delete">Удалить</button>
                </form>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>