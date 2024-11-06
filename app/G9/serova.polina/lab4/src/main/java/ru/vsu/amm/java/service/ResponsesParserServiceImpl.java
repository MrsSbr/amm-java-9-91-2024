package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.City;
import ru.vsu.amm.java.entity.Response;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


public final class ResponsesParserServiceImpl implements ResponsesParserService {

    private static final Logger logger;
    private final ResponsesService responsesService;

    static {
        logger = Logger.getLogger(ResponsesParserServiceImpl.class.getName());
    }

    public ResponsesParserServiceImpl(ResponsesService responsesService) {
        this.responsesService = responsesService;
        logger.log(Level.INFO, "Проинициализировали сервис парсинга ответов");
    }

    @Override
    public void parseFile(String filePath) {
        InputStream inputStream;
        try {
            inputStream = ResponsesParserService.class.getResourceAsStream(filePath);
            if (inputStream == null || inputStream.available() == 0) {
                throw new IllegalArgumentException("Не удалось открыть файл: " + filePath);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
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
                        responsesService.readResponse(data.city, data.respondentsCount, data.response);
                    });
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

            City city = new City(parts[0].trim());
            int respondentsCount = Integer.parseInt(parts[1].trim());
            Response response = new Response(parts[2].trim());

            return new ResponseData(city, respondentsCount, response);

        } catch (Exception e) {
            if (e.getClass() == NumberFormatException.class) {
                logger.log(Level.WARNING, "Ошибка преобразования количества респондентов в целое число: " + line, e);
            } else {
                logger.log(Level.WARNING, "Ошибка при чтении строки: " + line, e);
            }
            return null;
        }
    }

    private record ResponseData(City city, int respondentsCount, Response response) {}
}
