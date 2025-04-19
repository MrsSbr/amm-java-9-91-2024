<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Покупка билета</title>
</head>
<body>
<h1>Покупка билета на фильм</h1>
<form action="ticket" method="POST">
    <input type="hidden" name="filmId" value="${param.filmId}" />
    <label for="userId">Ваш ID:</label>
    <input type="number" id="userId" name="userId" required /><br/><br/>

    <label for="hallNumber">Номер зала:</label>
    <input type="number" id="hallNumber" name="hallNumber" required /><br/><br/>

    <label for="placeNumber">Номер места:</label>
    <input type="number" id="placeNumber" name="placeNumber" required /><br/><br/>

    <label for="startTime">Время начала:</label>
    <input type="datetime-local" id="startTime" name="startTime" required /><br/><br/>

    <label for="endTime">Время окончания:</label>
    <input type="datetime-local" id="endTime" name="endTime" required /><br/><br/>

    <button type="submit">Купить билет</button>
</form>
</body>
</html>