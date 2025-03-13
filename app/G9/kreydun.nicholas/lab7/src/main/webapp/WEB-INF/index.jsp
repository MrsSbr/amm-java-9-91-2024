<%--
  Created by IntelliJ IDEA.
  User: a123
  Date: 13.03.2025
  Time: 19:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>Create Post</h2>
<form action="post" method="post">
    Content: <input type="text" name="content" required />
    User ID: <input type="text" name="userId" required />
    <input type="submit" value="Create Post" />
</form>

<h2>All Posts</h2>
<c:forEach var="post" items="${posts}">
    <p>${post.content}</p>
</c:forEach>
</body>
</html>

