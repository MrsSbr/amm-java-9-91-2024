<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ include file="header.jspf" %>
<!doctype html>
<html lang="en" data-bs-theme="auto">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Таблица лидеров</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        thead tr:first-child th:first-child {
            border-top-left-radius: var(--bs-border-radius);
        }

        thead tr:first-child th:last-child {
            border-top-right-radius: var(--bs-border-radius);
        }

        tbody tr:last-child th:first-child {
            border-bottom-width: 0;
            border-bottom-left-radius: var(--bs-border-radius);
        }

        tbody tr:last-child td:last-child {
            border-bottom-right-radius: var(--bs-border-radius);
        }

        tbody tr:last-child td {
            border-bottom-width: 0;
        }

        th {
            text-align: center;
        }

        td {
            text-align: center;
        }
    </style>
</head>
<body class="bg-body-tertiary">
<main class="container" style="width: 40%">
    <div class="card w-50 mx-auto">
        <div class="card-body p-0">
            <table class="table mb-0">
                <thead class="table-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Игрок</th>
                    <th scope="col">Рейтинг</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${requestScope.topUsers}" varStatus="status">
                    <tr>
                        <th scope="row">${status.index + 1}</th>
                        <td><a href="/player/${user.nickname}">${user.nickname}</a></td>
                        <td><fmt:formatNumber value="${user.rating}" type="number" maxFractionDigits="0"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>