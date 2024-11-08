package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.City;
import ru.vsu.amm.java.entity.Response;
import ru.vsu.amm.java.storage.ResponsesStorage;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class ResponsesParserServiceImpl implements ResponsesParserService {

    private static final Logger logger;

    static {
        logger = Logger.getLogger(ResponsesParserServiceImpl.class.getName());
    }

    public ResponsesParserServiceImpl() {
        logger.log(Level.INFO, "Проинициализировали сервис парсинга ответов");
    }

    @Override
    public void parseFile(String filePath, ResponsesStorage responsesStorage) {
        try (InputStream inputStream = ResponsesParserService.class.getResourceAsStream(filePath)) {
            if (inputStream == null || inputStream.available() == 0) {
                throw new IllegalArgumentException("Не удалось открыть файл: " + filePath);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                reader.lines()
                        .map(ResponsesParserServiceImpl::parseLine)
                        .filter(Objects::nonNull)
                        .forEach(data -> {
                            logger.log(
                                    Level.INFO,
                                    "Считали из файла данные: Город: "
                                            + data.city + ", Респонденты: " + data.respondentsCount
                                            + ", Ответ: " + data.response
                            );
                            responsesStorage.insertResponse(data.city, data.respondentsCount, data.response);
                        });
            }
        } catch (IOException | IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Ошибка при чтении файла: " + filePath, e);
        }
        logger.log(Level.INFO, "Завершили чтение файла: " + filePath);
    }

    private static ResponseData parseLine(String line) {
        try {
            String[] parts = line.split(";");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Неверный формат строки: " + line);
            }

            City city = new City(parts[Constants.CITY_INDEX].trim());
            int respondentsCount = Integer.parseInt(parts[Constants.RESPONDENTS_INDEX].trim());
            Response response = new Response(parts[Constants.RESPONSE_INDEX].trim());

            return new ResponseData(city, respondentsCount, response);

        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Ошибка преобразования количества респондентов в целое число: " + line, e);
            return null;
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, "Ошибка при чтении строки: " + line, e);
            return null;
        }
    }

    private static class Constants {
        public static int CITY_INDEX = 0;
        public static int RESPONDENTS_INDEX = 1;
        public static int RESPONSE_INDEX = 2;
    }

    private record ResponseData(City city, int respondentsCount, Response response) {}
}
