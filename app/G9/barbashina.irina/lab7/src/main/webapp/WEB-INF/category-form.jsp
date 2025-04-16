<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${empty category.id ? 'Добавить категорию' : 'Редактировать категорию'}</title>
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
    <h1>${empty category.id ? 'Добавить категорию' : 'Редактировать категорию'}</h1>

    <form action="categories" method="post">
        <input type="hidden" name="id" value="${category.id}">
        <input type="hidden" name="action" value="${empty category.id ? 'insert' : 'update'}">

        <div class="form-group">
            <label for="name">Название категории:</label>
            <input type="text" id="name" name="name" value="${category.name}" required>
        </div>

        <div class="buttons">
            <button type="submit" class="btn btn-save">Сохранить</button>
            <a href="categories" class="btn btn-cancel">Отмена</a>
        </div>
    </form>
</div>
</body>
</html>