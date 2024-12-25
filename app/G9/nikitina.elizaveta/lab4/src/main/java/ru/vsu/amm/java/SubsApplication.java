package ru.vsu.amm.java;

import ru.vsu.amm.java.FileService.FileReader;
import ru.vsu.amm.java.Model.Month;
import ru.vsu.amm.java.Model.Subscriber;
import ru.vsu.amm.java.Service.Service;

import java.util.List;

public class SubsApplication {
    private static final String PATH = "app/G9/nikitina.elizaveta/lab4/src/main/java/ru/vsu/amm/java/Task.txt";

    private static final String PUBLICATIONS_COUNT = "\nby given month and name will find the publications to be delivered\n";
    private static final String PUBLICATIONS_PER_SUB = "\nusing the specified full name, print a list of subscription publications for this subscriber\n";
    private static final String AREA_NAME_WITH_BIGGEST_COUNT = "\ngiven name and month, determine the AREA that receives the most copies for delivery\n";

    private static final String PUBLICATIONS_COUNT_TITLE = "Business Review";
    private static final String PUBLICATIONS_PER_SUB_NAME = "Jessica Moore";
    private static final String AREA_TITLE = "Business Review";

    public static void main(String[] args) {

        FileReader reader = new FileReader();
        Service service = new Service();
        List<Subscriber> entities = reader.read(PATH);

        System.out.println(PUBLICATIONS_COUNT + service.getSubscriptionCountByMonthAndTitle(entities, PUBLICATIONS_COUNT_TITLE, Month.AUGUST));
        System.out.println(PUBLICATIONS_PER_SUB + service.getPublicationsBySubscriberName(entities, PUBLICATIONS_PER_SUB_NAME));
        System.out.println(AREA_NAME_WITH_BIGGEST_COUNT + service.getAreaWithMostSubscriptions(entities, AREA_TITLE, Month.JULY));

    }
}
