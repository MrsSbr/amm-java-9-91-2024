<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${empty article.id ? 'Создать статью' : 'Редактировать статью'}</title>
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
    <h1>${empty article.id ? 'Создать статью' : 'Редактировать статью'}</h1>
    <h1>${ error }</h1>
    <form action="${pageContext.request.contextPath}/articles" method="post">
        <input type="hidden" name="id" value="${article.id}">
        <input type="hidden" name="action" value="${empty article.id ? 'insert' : 'update'}">

        <div class="form-group">
            <label for="title">Заголовок:</label>
            <input type="text" id="title" name="title" value="${article.title}" required>
        </div>

        <div class="form-group">
            <label for="content">Содержание:</label>
            <textarea id="content" name="content" required>${article.content}</textarea>
        </div>

        <div class="form-group">
            <label for="category">Категория:</label>
            <select id="category" name="category" required>
                <option value="">-- Выберите категорию --</option>
                <c:forEach var="category" items="${categories}">
                    <option value="${category.id}" ${article.category.id == category.id ? 'selected' : ''}>
                            ${category.name}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="author">Автор:</label>
            <select id="author" name="author" required>
                <option value="">-- Выберите автора --</option>
                <c:forEach var="author" items="${authors}">
                    <option value="${author.id}" ${article.author.id == author.id ? 'selected' : ''}>
                            ${author.surname} ${author.name}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="buttons">
            <button type="submit" class="btn btn-save">Сохранить</button>
            <a href="${pageContext.request.contextPath}/articles" class="btn btn-cancel">Отмена</a>
        </div>
    </form>
</div>
</body>
</html>