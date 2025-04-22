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
            <th colspan="2">Введите данные жанра</th>
        </tr>
        <tr>
            <td>Название жанра:</td>
            <td>
                <input type="text" name="title" placeholder="Введите название жанра" required value='<c:if test="${not empty title}">${title}</c:if>'>
            </td>
        </tr>
    </table>

    <button type="submit"><%= request.getAttribute("button")%></button>
</form>

<a href="/genres">
    <button type="button" class="back-btn">Вернуться к списку жанров</button>
</a>
</body>
</html>
