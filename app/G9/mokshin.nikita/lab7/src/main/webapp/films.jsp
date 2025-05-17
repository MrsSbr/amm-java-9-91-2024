<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Films</title>
    <jsp:include page="style.jsp"/>
</head>
<body>

<h1>List films</h1>

<a href="/films/create">
    <button type="button" class="add-btn">Create film</button>
</a>

<table>
    <thead>
    <tr>
        <th>Title</th>
        <th>Slogan</th>
        <th>Description</th>
        <th>Release Date</th>
        <th>Genre</th>
        <th>Author</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="film" items="${films}">
        <tr>
            <td>${film.title}</td>
            <td>${film.slogan}</td>
            <td>${film.description}</td>
            <td>${film.releaseDate}</td>
            <td>
                <c:set var="title" value="${film.genreOptional.get().getTitle()}" />
                    ${title}
            </td>
            <td>
                <c:set var="name" value="${film.authorOptional.get().getName()}" />
                    ${name}
            </td>
            <td>
                <c:if test="${author.id == film.authorOptional.get().getId()}">
                <a href="/films/edit?id=${film.id}">
                    <button type="button">Edit</button>
                </a>
                <a href="/films/delete?id=${film.id}">
                    <button type="button" style="background-color: red">Delete</button>
                </a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
