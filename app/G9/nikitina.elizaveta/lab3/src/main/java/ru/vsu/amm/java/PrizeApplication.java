package ru.vsu.amm.java;

import ru.vsu.amm.java.Model.PrizeRecipient;
import ru.vsu.amm.java.Service.PrizeService;
import ru.vsu.amm.java.Util.PrizeGenerator;

import java.util.List;

public class PrizeApplication {

    private final static int PEOPLE_COUNT = 100;

    private final static String TASK_FIRST = "Departments that won the most:";
    private final static String TASK_SECOND = "\nEmployees who won the bonus:";
    private final static String TASK_THIRD = "\nNumber of employees who won only 1 time:";

    public static void main(String[] args) {

        PrizeService service = new PrizeService();
        List<PrizeRecipient> data = PrizeGenerator.generateRecipientList(PEOPLE_COUNT);

        // 1. Найти отдел (отделы), работники которого выигрывали чаще всего
        System.out.println(TASK_FIRST);
        System.out.println(service.findMostRecurringGroup(data));

        // 2. Список работников, которые выигрывали премию
        System.out.println(TASK_SECOND);
        System.out.println(service.countUniqueRecipients(data));

        // 3. Узнать количество работников, которые становились лучшими, только 1 раз
        System.out.println(TASK_THIRD);
        System.out.println(service.countRecipientsWithOneWin(data).orElse(-1L));
    }
}
