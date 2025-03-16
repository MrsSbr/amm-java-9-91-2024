<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Жанр</title>
    <jsp:include page="style.jsp"/>
</head>
<body>
<h1><%= request.getAttribute("h1").toString()%></h1>
<form action="${action}" method="post">
    <table>
        <tr>
            <th colspan="6">Введите данные фильма</th>
        </tr>
        <tr>
            <td>Название:</td>
            <td>
                <input type="text" name="title" placeholder="Название фильма" required value='<c:if test="${not empty film.title}">${film.title}</c:if>'>
            </td>
        </tr>
        <tr>
            <td>Слоган:</td>
            <td>
                <input type="text" name="slogan" placeholder="Слоган" required value='<c:if test="${not empty film.slogan}">${film.slogan}</c:if>'>
            </td>
        </tr>
        <tr>
            <td>Описание:</td>
            <td>
                <input type="text" name="description" placeholder="Описание" required value='<c:if test="${not empty film.description}">${film.description}</c:if>'>
            </td>
        </tr>
        <tr>
            <td>Дата релиза:</td>
            <td>
                <input type="date" name="releaseDate" required value='<c:if test="${not empty film.releaseDate}">${film.releaseDate}</c:if>'>
            </td>
        </tr>
        <tr>
            <td>Жанр:</td>
            <td>
                <c:if test="${not empty film}">
                    <c:set var="selectedGenreId" value="${film.genreOptional.get().getId()}" />
                </c:if>
                <label>
                    <select name="genre_id" required>
                        <c:forEach var="genre" items="${genres}">
                            <option value="${genre.id}" <c:if test="${genre.id == selectedGenreId}">selected</c:if>>
                                    ${genre.title}
                            </option>
                        </c:forEach>
                    </select>
                </label>
            </td>
        </tr>
    </table>

    <button type="submit"><%= request.getAttribute("button")%></button>
</form>

<a href="/films">
    <button type="button" class="back-btn">Вернуться к списку фильмов</button>
</a>
</body>
</html>
