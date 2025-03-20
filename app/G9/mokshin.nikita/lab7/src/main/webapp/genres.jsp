<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Жанры</title>
    <jsp:include page="style.jsp"/>
</head>
<body>

<h1>Список жанров</h1>

<a href="/genres/create">
    <button type="button" class="add-btn">Добавить жанр</button>
</a>

<table>
    <thead>
    <tr>
        <th>Название жанра</th>
        <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="genre" items="${genres}">
        <tr>
            <td>${genre.title}</td>
            <td>
                <a href="/genres/edit?id=${genre.id}">
                    <button type="button">Edit</button>
                </a>
                <a href="/genres/delete?id=${genre.id}">
                    <button type="button" style="background-color: red">Delete</button>
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
