<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<body>
<form method="post" action="articles">
    <input type="hidden" name="action"
           value="${empty article.id ? 'insert' : 'update'}">
    <input type="hidden" name="id" value="${article.id}">

    Title: <input name="title" value="${article.title}"><br>
    Text: <textarea name="content">${article.content}</textarea><br>
    <button>Save</button>
</form>
</body>
</html>