<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="java.time.format.DateTimeFormatter" %>

<html>
<head>
    <title>Книги</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        tr {
            background-color: #f5f5f5;
        }

        .action-buttons button {
            margin-right: 10px;
            padding: 5px 10px;
        }
    </style>
</head>
<body>
<h1>Список доступных книг</h1>

<c:if test="${not empty param.message}">
    <p style="color: green;">${param.message}</p>
</c:if>
<c:if test="${not empty param.error}">
    <p style="color: red;">${param.error}</p>
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
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${books}" var="book">
        <tr>
            <td>${book.title}</td>
            <td>${book.author}</td>
            <td>${book.publishedYear}</td>
            <td>${book.numberOfPages}</td>
            <td>${book.bookType}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="addBook.jsp" class="return-link">Добавить новую книгу</a></p>
</body>
</html>