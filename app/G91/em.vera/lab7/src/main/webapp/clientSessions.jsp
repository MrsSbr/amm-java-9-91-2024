<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Мои сессии</title>
  <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
  <style>
    :root {
      --gradient-start: #8e2de2;
      --gradient-end:   #4a00e0;
      --bg-card:        #ffffff;
      --text-dark:      #333333;
      --text-light:     #f5f5f5;
      --hover-light:    rgba(255,255,255,0.2);
    }
    * { box-sizing: border-box; margin: 0; padding: 0; }
    body { background: #f2f2f7; font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif; }
    .header {
      background: linear-gradient(135deg, var(--gradient-start), var(--gradient-end));
      padding: 1.5rem 2rem; display: flex; justify-content: space-between; align-items: center;
      border-radius: 0 0 1rem 1rem;
    }
    .header h1 { color: var(--text-light); font-size: 1.75rem; margin: 0; }
    .header .btn {
      background: var(--bg-card); color: var(--gradient-end); padding: 0.6rem 1.2rem;
      text-decoration: none; border-radius: 0.5rem; font-weight: bold; transition: background 0.3s;
    }
    .header .btn:hover { background: var(--hover-light); }
    .container {
      max-width: 900px; margin: -1rem auto 2rem; background: var(--bg-card);
      padding: 2rem; border-radius: 1rem; box-shadow: 0 4px 16px rgba(0,0,0,0.1);
    }
    table {
      width: 100%; border-collapse: collapse; margin-bottom: 1.5rem;
    }
    th, td { padding: 0.75rem 1rem; text-align: left; }
    th {
      background: linear-gradient(135deg, var(--gradient-start), var(--gradient-end));
      color: var(--text-light); font-weight: normal;
    }
    tr { background: #f9f9fb; }
    tr + tr td { border-top: 1px solid #ececec; }
    tr:hover { background: #ece8f9; }
    /* контекстное меню */
    #sessionMenu {
      display: none; position: absolute; background: #fff;
      border: 1px solid #ccc; border-radius: 0.5rem; box-shadow: 0 2px 8px rgba(0,0,0,0.15);
      z-index: 1000; min-width: 180px;
    }
    #sessionMenu button {
      width: 100%; padding: 0.6rem 1rem; border: none; background: none;
      text-align: left; cursor: pointer; font-size: 0.95rem;
    }
    #sessionMenu button:hover { background: #f0f0f0; }
  </style>
</head>
<body>
<div class="header">
  <h1>Мои сессии</h1>
  <a href="${pageContext.request.contextPath}/psychologist/login" class="btn">
    Войти как психолог
  </a>
</div>

<div class="container">
  <!-- Добавили id, чтобы JS мог найти таблицу -->
  <table id="sessionsTable">
    <thead>
    <tr>
      <th>Дата</th>
      <th>Цена</th>
      <th>Длительность (мин)</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="s" items="${sessions}">
      <!-- Добавили data-id -->
      <tr data-id="${s.idSession}">
        <td>${s.date}</td>
        <td>${s.price}</td>
        <td>${s.duration}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>

  <div class="actions">
    <a href="${pageContext.request.contextPath}/psychologist/list" class="btn">
      Поиск психологов
    </a>
  </div>
</div>

<!-- Контекстное меню -->
<div id="sessionMenu">
  <button type="button" onclick="viewDetails()">Просмотреть</button>
  <button type="button" onclick="editSession()">Изменить</button>
  <button type="button" onclick="cancelSession()">Отменить</button>
</div>

<script>
  const table     = document.getElementById('sessionsTable');
  const menu      = document.getElementById('sessionMenu');
  let   sessionId = null;

  // Вешаем слушатель клика на таблицу
  table.addEventListener('click', e => {
    const tr = e.target.closest('tr[data-id]');
    if (!tr) return;
    sessionId = tr.getAttribute('data-id');

    // Позиционируем меню под строкой
    const rect = tr.getBoundingClientRect();
    const containerRect = document.querySelector('.container').getBoundingClientRect();

    menu.style.top     = (rect.bottom - containerRect.top + 4) + 'px';
    menu.style.left    = (rect.left - containerRect.left) + 'px';
    menu.style.display = 'block';
  });

  // Скрываем меню при клике в любом другом месте
  document.addEventListener('click', e => {
    if (!menu.contains(e.target) && !e.target.closest('tr[data-id]')) {
      menu.style.display = 'none';
    }
  });

  function viewDetails() {
    window.location.href = '${pageContext.request.contextPath}/session/detail?id=' + sessionId;
  }
  function editSession() {
    window.location.href = '${pageContext.request.contextPath}/session/edit?id=' + sessionId;
  }
  function cancelSession() {
    if (!confirm('Отменить сессию №' + sessionId + '?')) return;
    fetch('${pageContext.request.contextPath}/session/cancel', {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: 'id=' + encodeURIComponent(sessionId)
    })
            .then(() => window.location.reload());
  }
</script>
</body>
</html>
