<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="ru.vsu.amm.java.util.DateTimeUtil" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
    <title>Note</title>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
        }

        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f4f8;
            height: 100vh;
            display: flex;
            flex-direction: column;
        }

        .note-container {
            max-width: 850px;
            width: 100%;
            margin: 0 auto;
            padding: 20px;
            flex-grow: 1;
            display: flex;
            flex-direction: column;
        }

        .note-content {
            background-color: white;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            flex-grow: 1;
            display: flex;
            flex-direction: column;
            height: 100%;
        }

        textarea {
            width: 100%;
            min-height: 100%;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 16px;
            resize: none;
        }

        #noteForm {
            height: 74vh;
        }

        .action-buttons {
            display: flex;
            gap: 10px;
            margin-top: 5px;
            flex-wrap: wrap;
            justify-content: space-between;
        }

        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            transition: all 0.3s;
        }

        .btn-save {
            background-color: #42a5f5;
            color: white;
        }

        .btn-save:hover {
            background-color: #1e88e5;
        }

        .btn-delete {
            background-color: #ef5350;
            color: white;
        }

        .btn-delete:hover {
            background-color: #d32f2f;
        }

        .btn-category {
            background-color: #66bb6a;
            color: white;
        }

        .btn-category:hover {
            background-color: #43a047;
        }

        .error-message {
            color: #d32f2f;
            background-color: #fce4e4;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 20px;
        }

        .category-info {
            margin-top: 10px;
            color: #666;
            font-style: italic;
        }

        .note-meta {
            margin-top: 15px;
            padding: 10px;
            background-color: #f8f9fa;
            border-radius: 4px;
        }

        .meta-item {
            display: flex;
            align-items: center;
            margin-bottom: 5px;
        }

        .meta-item:last-child {
            margin-bottom: 0;
        }

        .label {
            color: #666;
            font-weight: 500;
            min-width: 80px;
        }

        .value {
            color: #333;
        }

        .category-select {
            padding: 8px 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            margin-left: 10px;
            width: 200px;
        }

        .btn-update-category {
            background-color: #66bb6a;
            color: white;
            margin-left: 10px;
        }

        .btn-update-category:hover {
            background-color: #43a047;
        }
    </style>
</head>
<body>
<div class="note-container">
    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>

    <c:if test="${not empty note}">
        <form id="noteForm" action="${pageContext.request.contextPath}/note" method="post">
            <input type="hidden" name="id" value="${note.noteId}">
            <div class="note-content">
                <textarea name="content" placeholder="Write your note here..."><c:out
                        value="${note.content}"/></textarea>
            </div>

            <div class="note-meta">
                <div class="meta-item created-at">
                    <span class="label">Created:</span>
                    <span class="value">${DateTimeUtil.formatDateTime(note.createdAt)}</span>
                </div>

                <div class="meta-item updated-at">
                    <span class="label">Updated:</span>
                    <span class="value">${DateTimeUtil.formatDateTime(note.updatedAt)}</span>
                </div>

                <div class="meta-item category">
                    <span class="label">Category:</span>
                    <select name="categoryId" class="category-select">
                        <option value="null">No category</option>
                        <c:forEach items="${categories}" var="cat">
                            <option value="${cat.categoryId}"
                                ${note.categoryId eq cat.categoryId ? 'selected' : ''}>
                                    ${cat.title}
                            </option>
                        </c:forEach>
                    </select>
                    <button type="submit" name="action" value="update_category" class="btn btn-update-category">Save
                        Category
                    </button>
                </div>
            </div>

            <div class="action-buttons">
                <a href="${pageContext.request.contextPath}/notes" class="btn btn-save">Back to All Notes</a>

                <button type="submit" name="action" value="save" class="btn btn-save">Save</button>

                <button type="submit" name="action" value="delete" class="btn btn-delete">Delete</button>
            </div>
        </form>
    </c:if>

    <c:if test="${empty note}">
        <div class="error-message">Note not found or you don't have permission to view it</div>
    </c:if>
</div>

</body>
</html>