<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${empty author.id ? 'Добавить автора' : 'Редактировать автора'}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: #fff;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"], textarea, select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        textarea {
            height: 200px;
            resize: vertical;
        }
        .buttons {
            margin-top: 20px;
        }
        .btn {
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            color: white;
        }
        .btn-save {
            background-color: #4CAF50;
        }
        .btn-cancel {
            background-color: #f44336;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>${empty author.id ? 'Добавить автора' : 'Редактировать автора'}</h1>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <form action="authors" method="post">
        <input type="hidden" name="id" value="${author.id}">
        <input type="hidden" name="action" value="${empty author.id ? 'insert' : 'update'}">

        <div class="form-group">
            <label for="surname">Фамилия:</label>
            <input type="text" id="surname" name="surname" value="${author.surname}" required>
        </div>

        <div class="form-group">
            <label for="name">Имя:</label>
            <input type="text" id="name" name="name" value="${author.name}" required>
        </div>

        <div class="form-group">
            <label for="patronymic">Отчество:</label>
            <input type="text" id="patronymic" name="patronymic" value="${author.patronymic}">
        </div>

        <div class="form-group">
            <label for="registrationDate">Дата регистрации:</label>
            <input type="date" id="registrationDate" name="registrationDate"
                   value="${empty author.registrationDate ? today : author.registrationDate}" required>
        </div>

        <div class="buttons">
            <button type="submit" class="btn btn-save">Сохранить</button>
            <a href="authors" class="btn btn-cancel">Отмена</a>
        </div>
    </form>
</div>
</body>
</html>