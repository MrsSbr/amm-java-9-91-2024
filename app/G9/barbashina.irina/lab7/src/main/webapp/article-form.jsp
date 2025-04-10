<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${empty article.id ? 'Создать статью' : 'Редактировать статью'}</title>
    <style>
        .form-group { margin-bottom: 15px; }
        label { display: inline-block; width: 150px; }
        input, select, textarea { width: 300px; }
        textarea { height: 100px; }
        .error { color: red; }
    </style>
</head>
<body>
    <h2>${empty article.id ? 'Создать статью' : 'Редактировать статью'}</h2>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <form action="articles" method="post">
        <input type="hidden" name="action" value="${empty article.id ? 'insert' : 'update'}">
        <c:if test="${not empty article.id}">
            <input type="hidden" name="id" value="${article.id}">
        </c:if>

        <div class="form-group">
            <label for="title">Заголовок:</label>
            <input type="text" id="title" name="title" value="${article.title}" required>
        </div>

        <div class="form-group">
            <label for="content">Содержание:</label>
            <textarea id="content" name="content" required>${article.content}</textarea>
        </div>

        <div class="form-group">
            <label for="publicationDate">Дата публикации:</label>
            <input type="date" id="publicationDate" name="publicationDate"
                   value="${article.datePublication}" required>
        </div>

        <div class="form-group">
            <label for="authorId">Автор:</label>
            <select id="authorId" name="authorId" required>
                <option value="">-- Выберите автора --</option>
                <c:forEach var="author" items="${authors}">
                    <option value="${author.id}"
                            ${article.author.id == author.id ? 'selected' : ''}>
                        ${author.surname} ${author.name}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="categoryId">Категория:</label>
            <select id="categoryId" name="categoryId" required>
                <option value="">-- Выберите категорию --</option>
                <c:forEach var="category" items="${categories}">
                    <option value="${category.id}"
                            ${article.category.id == category.id ? 'selected' : ''}>
                        ${category.name}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <input type="submit" value="Сохранить">
            <a href="articles">Отмена</a>
        </div>
    </form>
</body>
</html>