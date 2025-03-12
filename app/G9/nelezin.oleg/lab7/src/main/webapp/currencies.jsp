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
    </style>
</head>
<body>
<h2>Available Currencies</h2>
<table>
    <thead>
    <tr>
        <th>Id</th>
        <th>Code</th>
        <th>Name</th>
        <th>Sign</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="currency" items="${currencies}">
        <tr>
            <td>${currency.id}</td>
            <td>${currency.code}</td>
            <td>${currency.name}</td>
            <td>${currency.sign}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<c:forEach var="cur" items="${currencies}">
    <h1>${cur.code}</h1>
</c:forEach>
</body>
</html>
