<%--
  Created by IntelliJ IDEA.
  User: globu
  Date: 20.05.2025
  Time: 17:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Трекер достижений</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }
        th {
            background-color: #f2f2f2;
        }
        .exit-btn {
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #f44336;
            color: white;
            border: none;
            cursor: pointer;
        }
        .exit-btn:hover {
            background-color: #d32f2f;
        }
    </style>
</head>
<body>
<h2>Пользователь: <%= session.getAttribute("login") %></h2>

<table>
    <tr>
        <th>Достижение</th>
        <th>Описание</th>
        <th>Статус</th>
    </tr>
    <%
        java.util.List<ru.vsu.amm.java.entity.EarnedAchievementView> achievements =
                (java.util.List<ru.vsu.amm.java.entity.EarnedAchievementView>) request.getAttribute("achievements");
        if (achievements != null && !achievements.isEmpty()) {
            for (ru.vsu.amm.java.entity.EarnedAchievementView achievement : achievements) {
    %>
    <tr>
        <td><%= achievement.getAchievement().getName() %></td>
        <td><%= achievement.getAchievement().getDescription() %></td>
        <td style="color: <%= "UNLOCKED".equals(String.valueOf(achievement.getEarned().getStatus())) ? "green" : "red" %>">
            <%= achievement.getEarned().getStatus() %>
        </td>
        <td>
            <%= achievement.getAchievement().getRequiredProgress() %> / <%= achievement.getEarned().getProgress() %>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="3">Достижения не найдены</td>
    </tr>
    <% } %>
</table>

<form action="logout" method="post">
    <input type="submit" class="exit-btn" value="Выход">
</form>
</body>
</html>