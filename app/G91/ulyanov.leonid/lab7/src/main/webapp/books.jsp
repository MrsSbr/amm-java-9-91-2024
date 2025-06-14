<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Книги</title>
    <link rel="stylesheet" href="css/books_style.css" />
</head>
<body>
<h1>Список доступных книг</h1>

<c:if test="${not empty param.message}">
    <p style="color: green; text-align:center;">${param.message}</p>
</c:if>
<c:if test="${not empty param.error}">
    <p style="color: red; text-align:center;">${param.error}</p>
</c:if>

<table>
    <thead>
    <tr>
        <th>Название</th>
        <th>Автор</th>
        <th>Издатель</th>
        <th>Год издания</th>
        <th>Количество страниц</th>
        <th>Тип книги</th>
        <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${books}" var="bookItem">
        <tr>
            <td>${bookItem.title}</td>
            <td>${bookItem.author}</td>
            <td>${bookItem.publisher}</td>
            <td>${bookItem.publishedYear}</td>
            <td>${bookItem.numberOfPages}</td>
            <td>${bookItem.bookType.ruName}</td>
            <td>
                <form class="action-form" action="delete" method="post">
                    <input type="hidden" name="userId" value="${sessionScope.user.userId}">
                    <input type="hidden" name="bookId" value="${bookItem.bookId}">
                    <button type="submit">Удалить</button>
                </form>
                <form class="action-form" action="update" method="get">
                    <input type="hidden" name="userId" value="${sessionScope.user.userId}">
                    <input type="hidden" name="bookId" value="${bookItem.bookId}">
                    <button type="submit">Изменить</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="create" class="return-link">Добавить новую книгу</a></p>
</body>
</html>