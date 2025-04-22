<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 18.04.2025
  Time: 21:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Игровой интерфейс</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }

        .game-container {
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .card {
            width: 300px;
            border: 2px solid #333;
            border-radius: 10px;
            padding: 20px;
            background-color: #f9f9f9;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            display: none; /* Скрыто по умолчанию */
        }

        .card-visible {
            display: block; /* Показываем когда нужно */
        }

        .card-field {
            margin-bottom: 10px;
        }

        .card-field input {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        .buttons-container {
            display: flex;
            justify-content: center;
            gap: 20px;
            padding: 20px;
            background-color: #eee;
        }

        .action-button {
            padding: 12px 24px;
            font-size: 16px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .action-button:hover {
            background-color: #45a049;
        }

        .hidden {
            display: none;
        }
        .card-row {
            display: flex;
            margin-bottom: 10px;
        }
        .card-field {
            margin-right: 10px;
        }
    </style>
</head>
<body>
<div class="game-container">
    <!-- Карточка (изначально скрыта) -->
    <div id="gameCard">
        <h3 id="cardTopic"></h3>
        <p id="cardDifficulty"></p>
        <div id="cardContent">
            <!-- Строки будут добавляться динамически -->
        </div>
    </div>
    <div id="diceResult" class="hidden">
        <h3>Результат броска: <span id="diceValue"></span></h3>
    </div>
</div>

<div class="buttons-container">
    <button id="generateCardBtn" class="action-button">Сгенерировать карточку</button>
    <button id="rollDiceBtn" class="action-button">Кинуть кубик</button>
</div>

<script>
    $(document).ready(function() {
        // Обработка генерации карточки
        $('#generateCardBtn').click(function() {
            $.ajax({
                url: '<%= request.getContextPath() %>/game',
                type: 'POST',
                data: { action: 'generateCard' },
                dataType: 'json',
                success: function(data) {
                    // Показываем карточку
                    $('#gameCard').removeClass('hidden');
                    $('#diceResult').addClass('hidden');

                    // Заполняем заголовок
                    $('#cardTopic').text('Тема: ' + data.topic);
                    $('#cardDifficulty').text('Сложность: ' + data.difficulty);

                    // Очищаем предыдущее содержимое
                    $('#cardContent').empty();

                    // Добавляем строки с действиями, словами и очками
                    data.words.forEach(function(word, index) {
                        const row = $(`
                            <div class="card-row">
                                <div class="card-field">
                                    <input type="text" value="${word.action}" readonly>
                                </div>
                                <div class="card-field">
                                    <input type="text" value="${word.word}" readonly>
                                </div>
                                <div class="card-field">
                                    <input type="text" value="${word.points}" readonly>
                                </div>
                            </div>
                        `);
                        $('#cardContent').append(row);
                    });
                },
                error: function(xhr) {
                    if (xhr.status === 401) {
                        window.location.href = '${pageContext.request.contextPath}/login';
                    } else {
                        alert("Ошибка при генерации карточки");
                    }
                }
            });
        });

        // Обработка броска кубика
        $('#rollDiceBtn').click(function() {
            $.ajax({
                url: '<%= request.getContextPath() %>/game', // Исправленный URL
                type: 'POST',
                data: { action: 'rollDice' },
                dataType: 'json',
                success: function(data) {
                    $('#diceResult').removeClass('hidden');
                    $('#diceValue').text(data.diceResult);
                },
                error: function(xhr) {
                    if (xhr.status === 401) {
                        window.location.href = '<%= request.getContextPath() %>/login';
                    } else {
                        alert("Ошибка при броске кубика");
                    }
                }
            });
        });
    });
</script>

</body>
</html>
