<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>Currency List</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        input {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<h2>Available Currencies</h2>
<table>
    <thead>
    <tr>
        <th>Code</th>
        <th>Name</th>
        <th>Sign</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="currency" items="${currencies}">
        <tr>
            <td>${currency.code}</td>
            <td>${currency.name}</td>
            <td>${currency.sign}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<form action="currencies" method="post">
    <input type="text" name="cur1"/>
    <span>First currency for exchange</span>
    <br>
    <input type="text" name="cur2"/>
    <span>Second currency for exchange</span>
    <br>
    <input type="number" name="amount">
    <span>Amount</span>
    <p>
        <input type="submit" value="Exchange">
    </p>
</form>
<% String result = (String) request.getAttribute("result"); %>
<% if (result != null) { %>
<div style="color: black;"><b><%= result %></b></div>
<% } %>

<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<% if (errorMessage != null) { %>
<div style="color: red;"><%= errorMessage %></div>
<% } %>
</body>
</html>
