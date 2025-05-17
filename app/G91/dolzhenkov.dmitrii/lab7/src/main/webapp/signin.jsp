<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign In</title>
</head>
<body>
<h2>Sign In</h2>

<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<% if (errorMessage != null) { %>
<p style="color:red;"><%= errorMessage %></p>
<% } %>

<form action="signin" method="post">
    <label for="login">Login:</label><br>
    <input type="text" id="login" name="login" required><br><br>

    <label for="password">Password:</label><br>
    <input type="password" id="password" name="password" required><br><br>

    <input type="submit" value="Sign In">
</form>

<a href="register">Don't have an account? Register</a>

</body>
</html>