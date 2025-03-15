<%--
  Created by IntelliJ IDEA.
  User: a123
  Date: 13.03.2025
  Time: 20:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>All Users</h2>
<c:forEach var="user" items="${users}">
    <p>${user.username}</p>
</c:forEach>
</body>
</html>

