<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Список статей</title>
    <style>
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .actions { white-space: nowrap; }
        .error { color: red; }
    </style>
</head>
<body>
    <h2>Список статей</h2>

    <a href="articles?action=new">Добавить новую статью</a>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Заголовок</th>
                <th>Дата публикации</th>
                <th>Категория</th>
                <th>Автор</th>
                <th class="actions">Действия</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="article" items="${articles}">
                <tr>
                    <td>${article.id}</td>
                    <td>${article.title}</td>
                    <td>${article.datePublication}</td>
                    <td>${article.category.name}</td>
                    <td>${article.author.surname} ${article.author.name}</td>
                    <td class="actions">
                        <a href="articles?action=edit&id=${article.id}">Редактировать</a>
                        <a href="articles?action=delete&id=${article.id}"
                           onclick="return confirm('Удалить эту статью?')">Удалить</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>