<%--
  Created by IntelliJ IDEA.
  User: Максим Гаршин
  Date: 23.04.2025
  Time: 20:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>New Note</title>
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
            height: 80vh;
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

        .btn-cancel {
            background-color: #9e9e9e;
            color: white;
        }

        .btn-cancel:hover {
            background-color: #757575;
        }

        .error-message {
            color: #d32f2f;
            background-color: #fce4e4;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="note-container">
    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>

    <form id="noteForm" action="${pageContext.request.contextPath}/notes" method="post">
        <div class="note-content">
            <textarea name="content" placeholder="Write your note here..." required></textarea>
        </div>

        <div class="action-buttons">
            <a href="${pageContext.request.contextPath}/notes" class="btn btn-cancel">Cancel</a>
            <button type="submit" class="btn btn-save">Create Note</button>
        </div>
    </form>
</div>
</body>
</html>