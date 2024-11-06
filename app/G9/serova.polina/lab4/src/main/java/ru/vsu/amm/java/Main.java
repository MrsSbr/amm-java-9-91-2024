package ru.vsu.amm.java;


import ru.vsu.amm.java.entity.City;
import ru.vsu.amm.java.entity.Response;
import ru.vsu.amm.java.service.ResponsesParserService;
import ru.vsu.amm.java.service.ResponsesParserServiceImpl;
import ru.vsu.amm.java.service.ResponsesService;
import ru.vsu.amm.java.service.ResponsesServiceImpl;
import ru.vsu.amm.java.storage.ResponsesStorage;
import ru.vsu.amm.java.storage.ResponsesStorageImpl;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        ResponsesStorage responsesStorage = new ResponsesStorageImpl();
        ResponsesService responsesService = new ResponsesServiceImpl(responsesStorage);
        ResponsesParserService responsesParserService = new ResponsesParserServiceImpl(responsesService);
        responsesParserService.parseFile("/responses.txt");
        Set<City> citiesWithoutPopularMoscowResponse = responsesService.getCitiesWithoutPopularMoscowResponse();
        if (citiesWithoutPopularMoscowResponse == null) {
            System.out.println("Не удалось найти город, где не встречался наиболее популярный ответ Москвы");
        } else if (citiesWithoutPopularMoscowResponse.isEmpty()) {
            System.out.println("Нет городов, где не встречался наиболее популярный ответ Москвы");
        } else {
            System.out.println("Города, где не встречался наиболее популярный ответ Москвы: " + citiesWithoutPopularMoscowResponse);
        }
        City mostVariousResponseCity = responsesService.getMostVariousResponseCity();
        if (mostVariousResponseCity == null) {
            System.out.println("Не удалось найти город с наибольшим количеством разнообразных ответов");
        } else {
            System.out.println("Город с наибольшим количеством разнообразных ответов: " + mostVariousResponseCity);
        }
        Response mostPopularResponseForCities = responsesService.getMostPopularResponseForCities("А");
        if (mostPopularResponseForCities == null) {
            System.out.println("Не удалось найти наиболее популярный ответ в городах, название которых начинается на "
                    + "А");
        } else {
            System.out.println("Наиболее наиболее популярный ответ в городах, название которых начинается на "
                    + "А" + ": " + mostPopularResponseForCities);
        }
    }
}