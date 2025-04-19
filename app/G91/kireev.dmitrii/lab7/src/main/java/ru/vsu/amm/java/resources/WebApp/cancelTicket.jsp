<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Отмена билета</title>
</head>
<body>
<h1>Отмена билета</h1>
<form action="ticket" method="DELETE">
    <label for="ticketId">ID билета для отмены:</label>
    <input type="number" id="ticketId" name="ticketId" required />
    <button type="submit">Отменить билет</button>
</form>
</body>
</html>