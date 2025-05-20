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
        .modal {
            display: none;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.3);
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
                <button class="btn btn-edit" onclick="openEditModal(${incident.idIncident})">Изменить</button>
                <form action="delete-incident" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="${incident.idIncident}">
                    <button type="submit" class="btn btn-delete">Удалить</button>
                </form>
            </div>
        </div>
    </c:forEach>
</div>

<div id="editModal" class="modal">
    <h2>Редактировать инцидент</h2>
    <form action="update-incident" method="post">
        <input type="hidden" id="editId" name="id">
        <div class="form-group">
            <label>Дата:</label>
            <input type="date" id="editDate" name="date" required>
        </div>
        <div class="form-group">
            <label>Описание:</label>
            <textarea id="editDescription" name="description" rows="4" required></textarea>
        </div>
        <button type="submit" class="btn">Сохранить</button>
        <button type="button" class="btn btn-cancel" onclick="closeEditModal()">Отмена</button>
    </form>
</div>

<script>
    function openEditModal(id) {
        fetch('get-incident?id=' + id)
            .then(response => response.json())
            .then(data => {
                document.getElementById('editId').value = data.idIncident;
                document.getElementById('editDate').value = data.dateOfIncident;
                document.getElementById('editDescription').value = data.description;
                document.getElementById('editModal').style.display = 'block';
            });
    }

    function closeEditModal() {
        document.getElementById('editModal').style.display = 'none';
    }
</script>
</body>
</html>