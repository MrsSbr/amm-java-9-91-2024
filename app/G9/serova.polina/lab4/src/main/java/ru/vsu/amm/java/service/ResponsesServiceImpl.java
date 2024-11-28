package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.City;
import ru.vsu.amm.java.entity.Response;
import ru.vsu.amm.java.storage.ResponsesStorage;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public final class ResponsesServiceImpl implements ResponsesService {

    private final static Logger logger;

    static {
        logger = Logger.getLogger(ResponsesParserServiceImpl.class.getName());
    }

    public ResponsesServiceImpl() {
        logger.log(Level.INFO, "Проинициализировали сервис обработки ответов");
    }

    @Override
    public Response getMostPopularResponseForCities(String startsWith, ResponsesStorage responsesStorage) {
        logger.log(
                Level.INFO,"Поиск наиболее популярного ответа в городах, название которых начинается на "
                        + startsWith
        );
        try {
            Optional<Response> result = responsesStorage.getResponses().entrySet().stream()
                    .filter(entry -> entry.getKey().name().startsWith(startsWith))
                    .flatMap(entry -> entry.getValue().entrySet().stream())
                    .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingInt(Map.Entry::getValue)))
                    .entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey);

            result.ifPresentOrElse(
                    r -> logger.log(
                            Level.INFO,
                            "Наиболее популярный ответ в городах, название которых начинается на " + startsWith + ": " + r
                    ),
                    () -> logger.log(
                            Level.WARNING,
                            "Нет городов, название которых начинается на " + startsWith
                    )
            );

            return result.orElse(null);
        } catch (Exception e) {
            logger.log(
                    Level.SEVERE,
                    "Не удалось найти наиболее популярный ответ в городах, название которых начинается на "
                            + startsWith,
                    e
            );
            return null;
        }
    }

    @Override
    public City getMostVariousResponseCity(ResponsesStorage responsesStorage) {
        logger.log(Level.INFO, "Поиск города с наибольшим количеством разнообразных ответов");
        try {
            Optional<City> result = responsesStorage.getResponses().entrySet().stream()
                    .max(Comparator.comparingInt(entry -> entry.getValue().size()))
                    .map(Map.Entry::getKey);

            result.ifPresentOrElse(
                    r -> logger.log(
                            Level.INFO,
                            "Город с наибольшим количеством разнообразных ответов: " + result
                    ),
                    () -> logger.log(Level.WARNING, "Нет городов в хранилище")
            );

            return result.orElse(null);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Не удалось найти город с наибольшим количеством разнообразных ответов", e);
            return null;
        }
    }

    @Override
    public Set<City> getCitiesWithoutPopularMoscowResponse(ResponsesStorage responsesStorage) {
        logger.log(Level.INFO, "Поиск городов, где не встречался наиболее популярный ответ Москвы");

        try {
            Optional<Response> moscowPopularResponse = responsesStorage.getResponses().entrySet().stream()
                    .filter(entry -> entry.getKey().name().equals("Москва"))
                    .flatMap(entry -> entry.getValue().entrySet().stream())
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey);

            moscowPopularResponse.ifPresentOrElse(
                            r -> logger.log(
                                        Level.INFO,
                                        "Самый популярный ответ в Москве: " + r
                            ),
                            () -> logger.log(Level.WARNING, "Москвы нет в хранилище")
                    );
            if (moscowPopularResponse.isEmpty()) {
                return null;
            }

            Set<City> result = responsesStorage.getResponses().entrySet().stream()
                    .filter(entry -> entry.getValue().keySet().stream().noneMatch(response -> response.equals(moscowPopularResponse.get())))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());
            if (result.isEmpty()) {
                logger.log(Level.INFO, "Нет городов, где не встречался наиболее популярный ответ Москвы");
            } else {
                logger.log(Level.INFO, "Города, где не встречался наиболее популярный ответ Москвы: " + result);
            }
            return result;
        } catch (Exception e) {
            logger.log(
                    Level.SEVERE,
                    "Не удалось найти город, где не встречался наиболее популярный ответ Москвы",
                    e
            );
            return null;
        }
    }
}
