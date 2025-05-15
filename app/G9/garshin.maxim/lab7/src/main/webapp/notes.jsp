<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>My Notes</title>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: 'Arial', sans-serif;
        }

        body {
            margin: 0;
            padding: 0;
            display: flex;
            background-color: #f5f5f5;
            height: 100vh;
        }

        /* Стили для сайдбара с категориями */
        .sidebar {
            width: 280px;
            background-color: #2c3e50;
            color: white;
            padding: 20px;
            display: flex;
            flex-direction: column;
            height: 100%;
        }

        .category-header {
            padding-bottom: 15px;
            border-bottom: 1px solid rgba(255, 255, 255, 0.1);
            margin-bottom: 15px;
        }

        .category-list-container {
            flex-grow: 1;
            overflow-y: auto;
            margin-bottom: 15px;
        }

        .category-item {
            background-color: #34495e;
            padding: 12px 15px;
            margin-bottom: 8px;
            border-radius: 4px;
            cursor: pointer;
            position: relative;
            transition: all 0.2s;
            color: #ecf0f1;
        }

        .category-item:hover {
            background-color: #3d566e;
        }

        .category-item.active {
            background-color: #3498db;
            color: white;
            font-weight: bold;
        }

        .delete-category-btn {
            position: absolute;
            right: 10px;
            top: 50%;
            transform: translateY(-50%);
            background: none;
            border: none;
            color: #bdc3c7;
            cursor: pointer;
            font-size: 18px;
            width: 24px;
            height: 24px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
            transition: all 0.2s;
        }

        .delete-category-btn:hover {
            color: #e74c3c;
            background-color: rgba(0, 0, 0, 0.1);
        }

        /* Стили для формы добавления категории */
        .add-category-form {
            margin-top: auto;
            padding-top: 15px;
            border-top: 1px solid rgba(255, 255, 255, 0.1);
        }

        .add-category-input {
            width: 100%;
            padding: 10px 12px;
            border: none;
            border-radius: 4px;
            margin-bottom: 10px;
            font-size: 14px;
            background-color: #34495e;
            color: white;
        }

        .add-category-input::placeholder {
            color: #95a5a6;
        }

        .add-category-button {
            width: 100%;
            padding: 10px;
            background-color: #27ae60;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
            transition: background-color 0.2s;
        }

        .add-category-button:hover {
            background-color: #2ecc71;
        }

        /* Стили для основной области с заметками */
        .notes-container {
            flex-grow: 1;
            padding: 25px;
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 20px;
            overflow-y: auto;
        }

        .note-card {
            background-color: white;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            cursor: pointer;
            transition: transform 0.2s, box-shadow 0.2s;
        }

        .note-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }

        .note-content {
            color: #333;
            line-height: 1.5;
            font-size: 15px;
        }

        /* Кнопка добавления заметки */
        .add-note-button {
            position: fixed;
            bottom: 30px;
            right: 30px;
            width: 60px;
            height: 60px;
            background-color: #e74c3c;
            color: white;
            border-radius: 50%;
            border: none;
            font-size: 24px;
            cursor: pointer;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
            transition: all 0.2s;
        }

        .add-note-button:hover {
            background-color: #c0392b;
            transform: scale(1.1);
        }

        /* Сообщения об ошибках */
        .error-message {
            color: #e74c3c;
            padding: 10px;
            background-color: #fdecea;
            border-radius: 4px;
            margin-bottom: 20px;
        }

        .category-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .category-item > span {
            flex-grow: 1;
            padding: 12px 0;
            cursor: pointer;
        }

        .category-item form {
            display: flex;
            align-items: center;
        }

        .modal {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 1000;
        }

        .modal-content {
            background: white;
            padding: 20px;
            border-radius: 8px;
            width: 300px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
        }

        .modal-actions {
            display: flex;
            justify-content: flex-end;
            gap: 10px;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div id="deleteModal" class="modal" style="display:none;">
    <div class="modal-content">
        <p>Delete this category?</p>
        <div class="modal-actions">
            <button onclick="document.getElementById('deleteModal').style.display='none'">Cancel</button>
            <form id="deleteForm" method="post" action="${pageContext.request.contextPath}/categories/delete">
                <input type="hidden" name="categoryId" id="modalCategoryId">
                <button type="submit">Delete</button>
            </form>
        </div>
    </div>
</div>

<div class="sidebar">
    <div class="category-header">
        <h2>My Categories</h2>
    </div>

    <div class="category-list-container">
        <div class="category-item ${empty param.categoryId ? 'active' : ''}"
             onclick="location.href='${pageContext.request.contextPath}/notes'">
            All Notes
        </div>

        <c:forEach items="${categories}" var="category">
            <div class="category-item ${param.categoryId eq category.categoryId ? 'active' : ''}">
                <span onclick="location.href='${pageContext.request.contextPath}/notes?categoryId=${category.categoryId}'">
                        ${category.title}
                </span>
                <form action="${pageContext.request.contextPath}/categories/delete" method="post"
                      onsubmit="return confirm('Delete category: ${category.title}?');">
                    <input type="hidden" name="categoryId" value="${category.categoryId}">
                    <button type="button" class="delete-category-btn"
                            onclick="showDeleteModal(${category.categoryId})">×
                    </button>
                </form>
            </div>
        </c:forEach>
    </div>

    <div class="add-category-form">
        <form action="${pageContext.request.contextPath}/categories/add" method="post">
            <input type="text" name="title" class="add-category-input" placeholder="New category name" required>
            <button type="submit" class="add-category-button">Add Category</button>
        </form>
    </div>
</div>

<div class="notes-container">
    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>

    <c:choose>
        <c:when test="${not empty notes}">
            <c:forEach items="${notes}" var="note">
                <div class="note-card"
                     onclick="location.href='${pageContext.request.contextPath}/note?id=${note.noteId}'">
                    <div class="note-content">
                        <%
                            String content = ((ru.vsu.amm.java.entities.Note) pageContext.getAttribute("note")).getContent();
                            String preview = content.length() > 150 ? content.substring(0, 150) + "..." : content;
                        %>
                        <%= preview %>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div style="grid-column: 1 / -1; text-align: center; padding: 40px;">
                <h3 style="color: #7f8c8d;">No notes found</h3>
                <p style="color: #95a5a6;">Create your first note</p>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<button class="add-note-button" onclick="location.href='${pageContext.request.contextPath}/notes/new'">+</button>
</body>

<script>
    function showDeleteModal(categoryId) {
        document.getElementById('modalCategoryId').value = categoryId;
        document.getElementById('deleteModal').style.display = 'flex';
    }
</script>

</html>