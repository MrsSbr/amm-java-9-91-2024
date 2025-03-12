package ru.vsu.amm.java.FileService;

import ru.vsu.amm.java.Model.Publication;
import ru.vsu.amm.java.Model.PublicationType;
import ru.vsu.amm.java.Model.Subscriber;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineParser {

    private static final Pattern PATTERN = Pattern.compile("^([A-Za-z]+ [A-Za-z]+),\\s([A-Za-z]+),\\s(\\d+ [A-Za-z]+ St),\\s(\\d+),\\s(.+)$");

    public Subscriber parse(String line) {

        Matcher matcher = PATTERN.matcher(line);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid line format");
        }

        int fullNameGroup = 1;
        int deliveryAreaGroup = 2;
        int addressGroup = 3;
        int numberOfSubscriptionsGroup = 4;
        int publicationsStringGroup = 5;

        int publicationLen = 4;

        String fullName = matcher.group(fullNameGroup);  // Имя и фамилия
        String deliveryArea = matcher.group(deliveryAreaGroup);  // Район доставки
        String address = matcher.group(addressGroup);  // Адрес
        int numberOfSubscriptions = Integer.parseInt(matcher.group(numberOfSubscriptionsGroup));  // Количество подписок
        String publicationsString = matcher.group(publicationsStringGroup);  // Строка с публикациями

        // Разделение строк публикаций и их обработка
        List<Publication> publications = new ArrayList<>();
        String[] publicationParts = publicationsString.split(";");

        for (int i = 0; i < publicationParts.length; i += publicationLen) {

            String title = publicationParts[i];
            PublicationType type = PublicationType.valueOf(publicationParts[i + 1]);

            LocalDate startDate = LocalDate.parse(publicationParts[i + 2]);
            LocalDate endDate = LocalDate.parse(publicationParts[i + 3]);

            publications.add(new Publication(title, type, startDate, endDate));
        }

        return new Subscriber(fullName, deliveryArea, address, numberOfSubscriptions, publications);
    }
}