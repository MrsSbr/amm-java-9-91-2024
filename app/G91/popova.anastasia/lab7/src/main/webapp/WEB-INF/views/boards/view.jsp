<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>${board.boardTitle}</title>
    <style>
        .header {
            background-color: #0079bf;
            color: white;
            padding: 16px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        .logo {
            font-size: 18pt;
            font-weight: bold;
        }
        .logout-button{
             background: none;
             border: none;
             color: white;
             font-weight: bold;
             font-size: 12.5 pt;
             cursor: pointer;
             margin-right: 20px;
        }
        .logout-form {
              display: inline;
              margin: 0;
              padding: 0;
        }
        .board-header {
            background-color: #018BDA;
            color: white;
            padding: 10px;
            margin-bottom: 20px;
            padding-right: 30px;
            display: flex;
            justify-content: space-between;
        }
        .link-button {
            color: white;
            text-decoration: none;
            border: none;
            cursor: pointer;
            font-weight: 14px;
            background: #ffffff33;
            padding: 10px 16px;
            border-radius: 4px;
        }
        .link-button-form {
            display: inline;
            margin: 0;
            padding: 0;
        }
        .link-button:hover {
            background-color: #CDDBE233;
        }
        .boards-link-container {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .column-head {
            flex: 1;
            min-width: 250px;
            max-width: calc(33.33% - 20px);
            display: inline-block;
            vertical-align: top;
            text-align: center;
            background: #ebecf0;
            color: #00253A;
            border-radius: 5px;
            padding: 10px;
        }
        .column-todo{
            background: #dEc8cc;
        }
        .column-in-progress {
            background: #e5d5c1;
        }
        .column-done {
            background: #c7d4c5;
        }
        .columns-container {
            display: flex;
            gap: 16px;
            padding: 0 20px;
            align-items: flex-start;
            justify-content: space-evenly;
            overflow-x: auto;
        }
        .error-message {
            color: #dc3545;
            text-align: center;
            margin-top: 16px;
        }
        .modal {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            justify-content: center;
            align-items: center;
            z-index: 1000;
        }
        .modal-content {
            background: white;
            padding: 32px;
            border-radius: 8px;
            max-width: 500px;
            width: 90%;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        .modal-title {
            font-size: 14.5pt;
            color: #172b4d;
            margin-bottom: 16px;
        }
        .form-group {
            margin-bottom: 16px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            color: #5e6c84;
            font-weight: bold;
        }
        input[type="text"],
        input[type="date"],
        textarea,
        select {
            width: 100%;
            padding: 12px;
            border: 1px solid #dfe1e6;
            border-radius: 4px;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }
        textarea {
            min-height: 100px;
            resize: vertical;
        }
        .btn {
            padding: 12px 24px;
            background-color: #0079bf;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .btn:hover {
            background-color: #026aa7;
        }
        .btn-cancel {
            background-color: #dfe1e6;
            color: #172b4d;
            margin-left: 16px;
        }
        .btn-cancel:hover {
            background-color: #c1c7d0;
        }
        .modal-error {
            color: #dc3545;
            margin-top: 8px;
            display: none;
        }
        .task-card {
            background: #ffffff77;
            border: none;
            border-radius: 4px;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
            padding: 10px;
            margin-bottom: 10px;
            display: flex;
            flex-direction: column;
            gap: 8px;
        }
        .task-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.2);
        }
        .task-title {
            font-weight: bold;
            font-size: 16px;
            color: #172b4d;
        }
        .task-description {
            font-size: 14.5pt;
            color: #5e6c84;
        }
        .task-dates {
            font-size: 14pt;
            color: #5e6c84;
        }
        .task-actions {
            display: flex;
            gap: 8px;
            margin-top: 8px;
        }
        .task-action-btn {
            padding: 6px 12px;
            font-size: 0.85rem;
            border-radius: 4px;
            cursor: pointer;
            border: none;
        }
        .edit-task-btn {
            background: #74996D;
            color: white;
        }
        .edit-task-btn:hover {
            background: #90BC89;
        }
        .delete-task-btn {
            background-color: #D18B97;
            color: white;
        }
        .delete-task-btn:hover {
            background-color: #DEC8CC;
        }
    </style>
</head>
<body>
    <div class="header">
        <div class="logo">Planner App</div>
        <div>
            <div>
                <form action="/auth/logout" method="POST" class="logout-form">
                    <button type="submit" class="logout-button">
                        Выйти из системы
                    </button>
                </form>
            </div>
        </div>
    </div>

    <div class="board-header">
         <div class="board-title-container">
            <h1>${board.boardTitle}</h1>
            <p>${empty board.boardDescription ? "~описание отсутствует~" : board.boardDescription}</p>
         </div>
         <div class="boards-link-container">
            <button type="button" class="link-button" onclick="openModal()">+ Создать задачу +</button>
            <form action="/boards/${board.boardID}/edit" method="GET" class="link-button-form">
                 <input type="hidden" name="referer" value="view">
                <button type="submit" class="link-button">Редактировать</button>
            </form>
            <form action="/boards" method="GET" class="link-button-form">
                <button type="submit" class="link-button">Назад к доскам</button>
            </form>
         </div>
    </div>

    <div class="columns-container">
        <c:forEach items="${columns}" var="column">
            <div class="column-head ${column.columnTitle == 'Сделать' ? 'column-todo' : column.columnTitle == 'В процессе' ? 'column-in-progress' : 'column-done'}">
                <h3>${column.columnTitle}</h3>
                <c:forEach items="${tasksByColumn[column.columnID]}" var="task">
                    <div class="task-card">
                        <div class="task-title">${(task.taskTitle)}</div>
                        <c:if test="${not empty task.taskDescription}">
                            <div class="task-description">${(task.taskDescription)}</div>
                        </c:if>
                        <c:if test="${not empty task.startDate or not empty task.endDate}">
                            <div class="task-dates">
                                <c:if test="${not empty task.startDate}">Начало: ${task.startDate}</c:if>
                                    <c:if test="${not empty task.endDate}"> | Конец: ${task.endDate}</c:if>
                            </div>
                        </c:if>
                        <div class="task-actions">
                            <button class="task-action-btn edit-task-btn" onclick="openEditModal('${task.taskID}', '${(task.taskTitle)}', '${(task.taskDescription)}', '${task.startDate}', '${task.endDate}', '${task.columnID}')">Редактировать</button>
                            <form action="/tasks/${task.taskID}" method="POST" class="link-button-form"
                                onsubmit="return confirm('Вы точно хотите удалить эту задачу?\nОтменить это действие нельзя!')">
                                <input type="hidden" name="_method" value="DELETE">
                                <button type="submit" class="task-action-btn delete-task-btn">Удалить</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:forEach>
    </div>

    <!-- Модальное окно для создания задачи -->
     <div id="createTaskModal" class="modal">
         <div class="modal-content">
            <h2 class="modal-title">Создать новую задачу</h2>
                <form id="createTaskForm" action="/tasks/create" method="POST">
                    <input type="hidden" name="boardId" value="${board.boardID}">
                    <div class="form-group">
                        <label for="columnId">Колонка *</label>
                        <select id="columnId" name="columnId" required>
                            <c:forEach items="${columns}" var="column">
                                <option value="${column.columnID}">${column.columnTitle}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="title">Название задачи *</label>
                        <input type="text" id="title" name="title" required>
                    </div>
                    <div class="form-group">
                        <label for="description">Описание</label>
                        <textarea id="description" name="description"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="startDate">Дата начала</label>
                        <input type="date" id="startDate" name="startDate">
                    </div>
                    <div class="form-group">
                        <label for="endDate">Дата окончания</label>
                        <input type="date" id="endDate" name="endDate">
                    </div>
                    <div class="modal-error" id="formError"></div>
                    <div>
                        <button type="submit" class="btn">Создать</button>
                        <button type="button" class="btn btn-cancel" onclick="closeModal()">Отмена</button>
                    </div>
                </form>
         </div>
     </div>

     <!-- Модальное окно для редактирования задачи -->
     <div id="editTaskModal" class="modal">
         <div class="modal-content">
             <h2 class="modal-title">Редактировать задачу</h2>
             <form id="editTaskForm" action="" method="POST">
                 <input type="hidden" name="_method" value="PUT">
                 <input type="hidden" name="taskId" id="editTaskId">
                 <div class="form-group">
                     <label for="editColumnId">Колонка *</label>
                     <select id="editColumnId" name="columnId" required>
                         <c:forEach items="${columns}" var="column">
                             <option value="${column.columnID}">${(column.columnTitle)}</option>
                         </c:forEach>
                     </select>
                 </div>
                 <div class="form-group">
                     <label for="editTitle">Название задачи *</label>
                     <input type="text" id="editTitle" name="title" required>
                 </div>
                 <div class="form-group">
                     <label for="editDescription">Описание</label>
                     <textarea id="editDescription" name="description"></textarea>
                 </div>
                 <div class="form-group">
                     <label for="editStartDate">Дата начала</label>
                     <input type="date" id="editStartDate" name="startDate">
                 </div>
                 <div class="form-group">
                     <label for="editEndDate">Дата окончания</label>
                     <input type="date" id="editEndDate" name="endDate">
                 </div>
                 <div class="modal-error" id="editFormError"></div>
                 <div>
                     <button type="submit" class="btn">Сохранить</button>
                     <button type="button" class="btn btn-cancel" onclick="closeEditModal()">Отмена</button>
                 </div>
             </form>
         </div>
     </div>

    <script>
            function openModal() {
                const modal = document.getElementById('createTaskModal');
                const errorDiv = document.getElementById('formError');
                errorDiv.textContent = '';
                document.getElementById('createTaskForm').reset();
                modal.style.display = 'flex';
            }

            function closeModal() {
                document.getElementById('createTaskModal').style.display = 'none';
                document.getElementById('createTaskForm').reset();
                document.getElementById('formError').textContent = '';
            }

            function openEditModal(taskId, title, description, startDate, endDate, columnId) {
                const modal = document.getElementById('editTaskModal');
                const errorDiv = document.getElementById('editFormError');
                const form = document.getElementById('editTaskForm');
                errorDiv.textContent = '';
                errorDiv.style.display = 'none';

                // Заполняем форму
                document.getElementById('editTaskId').value = taskId;
                document.getElementById('editTitle').value = title;
                document.getElementById('editDescription').value = description || '';
                document.getElementById('editStartDate').value = startDate || '';
                document.getElementById('editEndDate').value = endDate || '';
                document.getElementById('editColumnId').value = columnId;

                form.action = '/tasks/' + taskId;

                modal.style.display = 'flex';
            }

            function closeEditModal() {
                document.getElementById('editTaskModal').style.display = 'none';
                document.getElementById('editTaskForm').reset();
                document.getElementById('editFormError').textContent = '';
                document.getElementById('editFormError').style.display = 'none';
            }
    </script>

</body>
</html>