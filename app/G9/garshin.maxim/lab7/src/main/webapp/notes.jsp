<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Notes</title>
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
            display: flex;
            background-color: #f0f4f8;
        }

        .sidebar {
            width: 250px;
            background-color: #ff7f50;
            padding: 20px;
            box-shadow: 2px 0 5px rgba(0, 0, 0, 0.2);
        }

        .category-list {
            margin-top: 20px;
        }

        .category-item {
            background-color: #fff;
            padding: 10px;
            margin-bottom: 10px;
            border-radius: 5px;
            transition: background-color 0.3s;
            cursor: pointer;
        }

        .category-item:hover {
            background-color: #ffe4b5;
        }

        .category-item.active {
            background-color: #60bdf7;
            color: cornsilk;
            font-weight: bold;
        }

        .notes-container {
            flex-grow: 1;
            padding: 20px;
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
        }

        .note-card {
            background-color: #87cefa;
            border-radius: 10px;
            padding: 15px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
            transition: transform 0.3s, box-shadow 0.3s;
            cursor: pointer;
        }

        .note-card:hover {
            transform: scale(1.05);
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
        }

        .note-content {
            color: #333;
            line-height: 1.5;
        }

        .error-message {
            color: red;
            margin-bottom: 20px;
        }

        .add-note-button {
            position: fixed;
            bottom: 30px;
            right: 30px;
            width: 60px;
            height: 60px;
            background-color: #ff7f50;
            color: white;
            border-radius: 50%;
            border: none;
            font-size: 24px;
            cursor: pointer;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
            transition: background-color 0.3s, transform 0.3s;
        }

        .add-note-button:hover {
            background-color: #ff6347;
            transform: scale(1.1);
        }
    </style>
</head>
<body>
<div class="sidebar">
    <h2>My Categories</h2>
    <div class="category-list">
        <div class="category-item <%= (request.getParameter("categoryId") == null || request.getParameter("categoryId").equals("all")) ? "active" : "" %>"
             onclick="location.href='${pageContext.request.contextPath}/notes?categoryId=all'">
            All
        </div>

        <%
            java.util.List<ru.vsu.amm.java.entities.Category> categories =
                    (java.util.List<ru.vsu.amm.java.entities.Category>) request.getAttribute("categories");
            if (categories != null) {
                String currentCategoryId = request.getParameter("categoryId");
                for (ru.vsu.amm.java.entities.Category category : categories) {
                    boolean isActive = currentCategoryId != null && currentCategoryId.equals(String.valueOf(category.getCategoryId()));
        %>
        <div class="category-item <%= isActive ? "active" : "" %>"
             onclick="location.href='${pageContext.request.contextPath}/notes?categoryId=<%= category.getCategoryId() %>'">
            <%= category.getTitle() %>
        </div>
        <% }
        } %>
    </div>
</div>

<div class="notes-container">
    <% String error = (String) request.getAttribute("error");
        if (error != null) { %>
    <div class="error-message">
        <%= error %>
    </div>
    <% } %>

    <% java.util.List<ru.vsu.amm.java.entities.Note> notes =
            (java.util.List<ru.vsu.amm.java.entities.Note>) request.getAttribute("notes");
        if (notes != null && !notes.isEmpty()) {
            for (ru.vsu.amm.java.entities.Note note : notes) {
                String content = note.getContent();
                // Оставляем только текст заметки, без отдельного заголовка
                String preview = content.length() > 150 ? content.substring(0, 150) + "..." : content; %>
    <div class="note-card" onclick="location.href='${pageContext.request.contextPath}/note?id=<%= note.getNoteId() %>'">
        <div class="note-content"><%= preview %>
        </div>
    </div>
    <% }
    } else { %>
    <div style="width: 100%; text-align: center; margin-top: 50px;">
        <h3>No notes yet</h3>
        <p>Create your first note</p>
    </div>
    <% } %>
</div>

<button class="add-note-button" onclick="location.href='${pageContext.request.contextPath}/notes/new'">+</button>
</body>
</html>