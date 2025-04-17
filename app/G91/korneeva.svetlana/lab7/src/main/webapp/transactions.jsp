<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Транзакции</title>
    <jsp:include page="style.jsp"/>
</head>
<body>

<div class="new-transaction-form">
    <h3>Новая транзакция</h3>
    <form action="transactions" method="post">
        <input type="number" step="0.01" name="amount" placeholder="Сумма" required />

        <select name="type" required>
            <option value="" disabled selected>Тип</option>
            <option value="true">Доход</option>
            <option value="false">Расход</option>
        </select>

        <input type="datetime-local" name="date" required />

        <select name="category" required>
            <option value="" disabled selected>Категория</option>
            <c:forEach var="category" items="${categories}">
                <option value="${category}" ${category == param.category ? 'selected' : ''}>
                        ${category}
                </option>
            </c:forEach>
        </select>

        <input type="submit" value="Добавить">
    </form>
    <c:if test="${not empty errorSave}">
        <div class="error-message">${errorSave}</div>
    </c:if>
</div>

<div class="summary">
    <h2>Общая информация</h2>
    <p><strong>Доходы:</strong> ${response.revenue}</p>
    <p><strong>Расходы:</strong> ${response.expenses}</p>
    <p><strong>Итог:</strong> ${response.total}</p>
</div>

<div class="date-filter-form">
    <form action="transactions" method="get">
        <label for="from">С:</label>
        <input type="date" id="from" name="from" value="${param.from}">

        <label for="to">По:</label>
        <input type="date" id="to" name="to" value="${param.to}">

        <input type="submit" value="Фильтровать">
    </form>
    <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
    </c:if>
</div>

<div class="transaction-table">
    <h2>Транзакции</h2>
    <table>
        <thead>
        <tr>
            <th>Сумма</th>
            <th>Тип</th>
            <th>Дата</th>
            <th>Категория</th>
            <th>Действие</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="tx" items="${response.transactions}">
            <tr>
                <td>${tx.amount}</td>
                <td>
                    <c:choose>
                        <c:when test="${tx.type}">Доход</c:when>
                        <c:otherwise>Расход</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:out value="${tx.date}"/>
                </td>
                <td>
                    <c:out value="${tx.category}" default="N/A"/>
                </td>
                <td>
                    <form action="transactions/delete" method="get">
                        <input type="hidden" name="id" value="${tx.id}" />
                        <input type="submit" value="Удалить" class="delete-btn"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
