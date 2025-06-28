<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавить книгу</title>
    <link rel="stylesheet" href="css/addBook_style.css" />
</head>
<body>
<h2>Добавить новую книгу</h2>

<c:if test="${not empty param.error}">
    <p style="color: red; text-align:center;">${param.error}</p>
</c:if>

<form action="create" method="post">
    <input type="hidden" name="Id_user" value="${sessionScope.user.userId}">
    Название: <input type="text" name="title" required><br><br>
    Автор: <input type="text" name="author" required><br><br>
    Издательство: <input type="text" name="publisher" required><br><br>
    Год издания: <input type="number" name="publishedYear" required><br><br>
    Количество страниц: <input type="number" name="numberOfPages" required><br><br>
    Тип книги:
    <select name="bookType" required>
        <c:forEach var="t" items="${bookTypes}">
            <option value="${t.name()}">${t.ruName}</option>
        </c:forEach>
    </select>
    <br><br>
    <input type="submit" value="Добавить">
</form>

<br>
<a href="read">Обратно на главную</a>

</body>
</html>