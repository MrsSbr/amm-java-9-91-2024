<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Scooter</title>
</head>
<body>
<h2>Add new Scooter</h2>

<form action="addScooter" method="post">
    Model: <input type="text" name="model" required><br><br>
    Latitude: <input type="text" name="latitude" required><br><br>
    Longitude: <input type="text" name="longitude" required><br><br>
    <input type="submit" value="Add Scooter">
</form>

<% String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) { %>
<p style="color: red"><%= errorMessage %></p>
<% } %>

<br>
<a href="index.jsp">Back to Main Page</a>

</body>
</html>