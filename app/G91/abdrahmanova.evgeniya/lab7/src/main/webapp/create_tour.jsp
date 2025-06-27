<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Добавить новый тур</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            background: #f8f9fa;
            padding: 30px 20px;
            color: #333;
            min-height: 100vh;
        }

        .container {
            max-width: 1000px;
            margin: 0 auto;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 40px;
            padding: 25px 30px;
            background: white;
            border-radius: 12px;
            box-shadow: 0 6px 25px rgba(0, 0, 0, 0.08);
        }

        .page-title {
            font-size: 32px;
            font-weight: 700;
            display: flex;
            align-items: center;
            gap: 15px;
            color: #ff7043;
        }

        .form-container {
            background: white;
            border-radius: 12px;
            box-shadow: 0 8px 30px rgba(0, 0, 0, 0.1);
            padding: 40px;
            margin-bottom: 40px;
        }

        .form-title {
            font-size: 24px;
            margin-bottom: 30px;
            color: #333;
            padding-bottom: 15px;
            border-bottom: 2px solid #f0f0f0;
        }

        .form-group {
            margin-bottom: 25px;
        }

        .form-label {
            display: block;
            margin-bottom: 10px;
            font-weight: 600;
            color: #555;
            font-size: 16px;
        }

        .form-input, .form-select, .form-textarea {
            width: 100%;
            padding: 14px 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 16px;
            transition: all 0.3s;
        }

        .form-input:focus, .form-select:focus, .form-textarea:focus {
            outline: none;
            border-color: #ff7043;
            box-shadow: 0 0 0 3px rgba(255, 112, 67, 0.2);
        }

        .form-textarea {
            min-height: 150px;
            resize: vertical;
        }

        .form-row {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 25px;
            margin-bottom: 25px;
        }

        .actions {
            display: flex;
            justify-content: flex-end;
            gap: 20px;
            margin-top: 30px;
            padding-top: 30px;
            border-top: 1px solid #f0f0f0;
        }

        .btn {
            padding: 14px 28px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .btn:hover {
            transform: translateY(-3px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }

        .btn-back {
            background: #f0f0f0;
            color: #333;
        }

        .btn-back:hover {
            background: #e0e0e0;
        }

        .btn-save {
            background: #ff7043;
            color: white;
        }

        .btn-save:hover {
            background: #ff5722;
        }

        .form-hint {
            font-size: 14px;
            color: #888;
            margin-top: 6px;
        }

        .required:after {
            content: " *";
            color: #ff7043;
        }

        .image-preview {
            width: 200px;
            height: 150px;
            border: 2px dashed #ddd;
            border-radius: 8px;
            margin-top: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
            background: #fafafa;
        }

        .image-preview img {
            max-width: 100%;
            max-height: 100%;
            display: none;
        }

        .image-upload {
            display: flex;
            flex-direction: column;
            align-items: flex-start;
        }

        .image-upload-btn {
            background: #f0f7ff;
            color: #0aa;
            padding: 10px 20px;
            border-radius: 6px;
            cursor: pointer;
            margin-top: 10px;
            font-weight: 500;
            transition: background 0.3s;
        }

        .image-upload-btn:hover {
            background: #e0f0ff;
        }

        @media (max-width: 768px) {
            .form-row {
                grid-template-columns: 1fr;
                gap: 15px;
            }

            .form-container {
                padding: 25px;
            }

            .actions {
                flex-direction: column;
            }

            .btn {
                width: 100%;
                justify-content: center;
            }
        }

        @media (max-width: 480px) {
            .header {
                flex-direction: column;
                gap: 20px;
                text-align: center;
            }

            .page-title {
                justify-content: center;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1 class="page-title">
            <span>📝</span> Добавить новый тур
        </h1>
    </div>

    <div class="form-container">
        <h2 class="form-title">Информация о туре</h2>

        <form id="tourForm" method="post" action="/create_tour">
            <div class="form-row">
                <div class="form-group">
                    <label class="form-label required">Название тура</label>
                    <input type="text" class="form-input" name="title" placeholder="Введите название тура" required>
                </div>

                <div class="form-group">
                    <label class="form-label required">Цена (руб.)</label>
                    <input type="number" class="form-input" name="price" placeholder="Введите цену" min="0" step="100" required>
                </div>
            </div>

            <div class="form-group">
                <label class="form-label required">Описание тура</label>
                <textarea class="form-textarea" name="description" placeholder="Подробное описание тура..." required></textarea>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label class="form-label required">Продолжительность (часы)</label>
                    <input type="number" class="form-input" name="duration" placeholder="Введите количество часов" min="1" required>
                    <div class="form-hint">Общее время проведения тура</div>
                </div>

                <div class="form-group">
                    <label class="form-label required">Макс. участников</label>
                    <input type="number" class="form-input" name="maxParticipants" placeholder="Введите количество" min="1" required>
                    <div class="form-hint">Максимальное количество участников</div>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label class="form-label required">Место начала</label>
                    <input type="text" class="form-input" name="startLocation" placeholder="Город, адрес, ориентир" required>
                </div>

                <div class="form-group">
                    <label class="form-label required">Язык тура</label>
                    <select class="form-select" name="language" required>
                        <option value="" disabled selected>Выберите язык</option>
                        <option value="RUSSIAN">Русский</option>
                        <option value="ENGLISH">Английский</option>
                        <option value="CHINESE">Китайский</option>
                        <option value="SPANISH">Испанский</option>
                        <option value="GERMAN">Немецкий</option>
                        <option value="FRENCH">Французский</option>
                    </select>
                </div>
            </div>

            <div class="actions">
                <button type="button" class="btn btn-back" onclick="local.href">
                    <span>←</span> Назад
                </button>
                <button type="submit" class="btn btn-save">
                    <span>💾</span> Сохранить тур
                </button>
            </div>
        </form>
    </div>
</div>

<script>
    document.getElementById('imageUpload').addEventListener('change', function(e) {
        const preview = document.getElementById('previewImage');
        const placeholder = document.getElementById('previewPlaceholder');
        const file = e.target.files[0];

        if (file) {
            const reader = new FileReader();

            reader.onload = function(e) {
                preview.src = e.target.result;
                preview.style.display = 'block';
                placeholder.style.display = 'none';
            }

            reader.readAsDataURL(file);
        } else {
            preview.style.display = 'none';
            placeholder.style.display = 'block';
        }
    });

    document.getElementById('tourForm').addEventListener('submit', function(e) {
        const requiredFields = this.querySelectorAll('[required]');
        let isValid = true;

        requiredFields.forEach(field => {
            if (!field.value.trim()) {
                isValid = false;
                field.style.borderColor = '#ff7043';
            } else {
                field.style.borderColor = '#ddd';
            }
        });

        if (!isValid) {
            e.preventDefault();
            alert('Пожалуйста, заполните все обязательные поля!');
        }
    });
</script>
</body>
</html>