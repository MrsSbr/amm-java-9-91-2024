package ru.vsu.amm.java.evaluation;

import java.util.List;
import java.util.Set;

public class TaskLab3 {
    public static void main(String[] args) {
        // Генерация статистики по 9456 кампаниям
        List<Campaign> campaigns = CampaignGenerator.generateCampaigns(9456);

        // Средняя длительность кампании
        double averageDuration = CampaignAnalyzer.calculateAverageDuration(campaigns);
        System.out.println("Средняя длительность кампании: " + averageDuration + " дней");

        // Типы кампаний за последний год
        Set<String> campaignTypesLastYear = CampaignAnalyzer.getCampaignTypesLastYear(campaigns);
        System.out.println("Типы кампаний за последний год: " + campaignTypesLastYear);

        // Лучшая кампания по соотношению бюджет/охват
        Campaign bestCampaign = CampaignAnalyzer.findBestCampaign(campaigns);
        if (bestCampaign != null) {
            System.out.println("Лучшая кампания по соотношению бюджет/охват: ");
            System.out.println("Тип: " + bestCampaign.getType());
            System.out.println("Дата начала: " + bestCampaign.getStartDate());
            System.out.println("Дата окончания: " + bestCampaign.getEndDate());
            System.out.println("Охват: " + bestCampaign.getReach());
            System.out.println("Бюджет: " + bestCampaign.getBudget());
            System.out.println("Соотношение бюджет/охват: " + (bestCampaign.getBudget() / bestCampaign.getReach()));
        } else {
            System.out.println("Не удалось найти кампанию");
        }
    }
}
