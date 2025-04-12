<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Список статей</title>
</head>
<body>
<h2>Список статей</h2>

<a href="articles?action=new">Добавить статью</a>
<c:if test="${not empty error}"><p style="color:red">${error}</p></c:if>

<table border="1">
    <tr>
        <th>Заголовок</th>
        <th>Дата</th>
        <th>Действия</th>
    </tr>
    <c:forEach items="${articles}" var="article">
        <tr>
            <td>${article.title}</td>
            <td>${article.datePublication}</td>
            <td>
                <a href="articles?action=edit&id=${article.id}">✏️</a>
                <a href="articles?action=delete&id=${article.id}"
                   onclick="return confirm('Удалить?')">🗑️</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>