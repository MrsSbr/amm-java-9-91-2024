<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Изменение книги</title>
    <link rel="stylesheet" href="css/addBook_style.css" />
</head>
<body>
<h2>Изменить книгу</h2>

<c:if test="${not empty param.error}">
    <p style="color: red; text-align:center;">${param.error}</p>
</c:if>

<form action="update" method="post">
    <input type="hidden" name="userId" value="${param.userId}">
    <input type="hidden" name="bookId" value="${param.bookId}">

    Название: <input type="text" name="title" value="${book.title}" required><br><br>
    Автор: <input type="text" name="author" value="${book.author}" required><br><br>
    Издательство: <input type="text" name="publisher" value="${book.publisher}" required><br><br>
    Год издания: <input type="number" name="publishedYear" value="${book.publishedYear}" required><br><br>
    Количество страниц: <input type="number" name="numberOfPages" value="${book.numberOfPages}" required><br><br>
    Тип книги:
    <select name="bookType" required>
        <c:forEach var="t" items="${bookTypes}">
            <option value="${t.name()}" <c:if test="${book != null && t == book.bookType}">selected="selected"</c:if>>${t.ruName}</option>
        </c:forEach>
    </select>
    <br><br>
    <input type="submit" value="Изменить">
</form>

<br>
<a href="read">Обратно на главную</a>

</body>
</html>