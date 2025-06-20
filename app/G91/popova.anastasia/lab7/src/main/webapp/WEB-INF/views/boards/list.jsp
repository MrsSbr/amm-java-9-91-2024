<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
    <head>
        <title>Мои доски</title>
        <meta charset="UTF-8">
        <style>
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
            .board-container {
                display: flex;
                flex-wrap: wrap;
                gap: 20px;
                padding: 20px;
                color: #A4AFB2;
            }
            .board-card {
                border: 1px solid #ddd;
                border-radius: 5px;
                padding: 15px;
                width: 250px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                transition: transform 0.2s;
                background-color: #f9f9f9;
            }
            .board-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 4px 8px rgba(0,0,0,0.2);
            }
            .link-button {
                color: white;
                background: #CDDBE233;
                border: none;
                padding: 6px 10px;
                border-radius: 4px;
                cursor: pointer;
                color: #00253A;
                font-size: 14px;
                transition: background-color 0.2s;
            }
            .link-button:hover {
                background: #ffffff33;
            }
            .delete-button {
                background: #D18B97;
                color: white;
            }
            .delete-button:hover {
                background: #DEC8CC;
            }
            .board-title {
                font-size: 14.5pt;
                font-weight: bold;
                margin-bottom: 10px;
                color: #333;
            }
            .board-description {
                color: #666;
                margin-bottom: 15px;
            }
            .link-button-container {
                display: flex;
                justify-content: space-between;
                gap: 5px;
            }
            .link-button-form {
                display: inline;
                margin: 0;
                padding: 0;
            }
            .create-btn-container {
                display: flex;
                align-items: center;
            }
            .create-btn {
                margin: 20px;
                padding: 10px 15px;
                background-color: #74996D;
                color: white;
                text-decoration: none;
                border-radius: 4px;
            }
            .create-btn:hover {
                background-color: #90BC89;
            }
            .header {
                background-color: #0079bf;
                color: white;
                padding: 16px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }
            .under_header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 10px 20px;
                margin-bottom: 20px;
                padding-right: 15px;
                background-color: #f0f0f0;
            }
            .logo {
                font-size: 24px;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <div class="header">
                <div class="logo">Planner App</div>
                <div>
                    <form action="/auth/logout" method="POST" class="logout-form">
                        <button type="submit" class="logout-button">
                            Выйти из системы
                        </button>
                    </form>
                </div>
            </div>

        <div class="under_header">
            <h1>Мои доски</h1>
            <div class="create-btn-container">
                <a href="${pageContext.request.contextPath}/boards/create" class="create-btn">Создать доску</a>
            </div>
        </div>

         <c:if test="${not empty error}">
             <div class="error-message">
                <c:out value="${error}"/>
             </div>
         </c:if>

        <div class="board-container">
            <c:choose>
                <c:when test="${empty boards}">
                    <p>Доски не найдены<br>Создайте свою первую доску!</p>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${boards}" var="board">
                        <div class="board-card"  data-board-id="${board.boardID}">
                            <div class="board-title">${board.boardTitle}</div>
                            <div class="board-description">
                                <c:if test="${not empty board.boardDescription}">
                                    ${board.boardDescription}
                                </c:if>
                                <c:if test="${empty board.boardDescription}">
                                    ~описание отсутствует~
                                </c:if>
                            </div>
                            <div class="link-button-container">
                                <form action="/boards/${board.boardID}" method="GET" class="link-button-form">
                                    <button type="submit" class="link-button">Открыть</button>
                                </form>

                                <form action="/boards/${board.boardID}/edit" method="GET" class="link-button-form">
                                      <input type="hidden" name="referer" value="list">
                                     <button type="submit" class="link-button">Изменить</button>
                                </form>
                                <form action="/boards/${board.boardID}" method="POST" class="link-button-form"
                                     onsubmit="return confirm('Вы точно хотите удалить эту доску?\nОтменить это действие нельзя!')">
                                    <input type="hidden" name="_method" value="DELETE">
                                    <button type="submit" class="link-button delete-button">Удалить</button>
                                </form>

                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>

    </body>
</html>