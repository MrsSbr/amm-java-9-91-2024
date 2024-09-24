package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class CourtApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a number of records: ");
        int n = scanner.nextInt();
        List<CaseRecord> records = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            records.add(CaseRecordGenerator.generateRecord());
        }

        CaseRecordService caseRecordService = new CaseRecordService();

        System.out.print("Count the number of people who participated in the trials" +
                " but were not convicted: ");
        System.out.print(caseRecordService.getNotConvictedPeople(records).size());
        System.out.println('\n');

        System.out.println("Find people who participated in the processes" +
                " in more than 1 article over the past 10 years:");
        Set<String> peopleWhoParticipated = caseRecordService.getPeopleMultipleArticles(records);
        int i = 0;
        for (String name : peopleWhoParticipated) {
            if (i == peopleWhoParticipated.size() - 1) {
                System.out.print(name);
            } else {
                System.out.print(name + ", ");
            }
            i++;
        }
        System.out.println('\n');

        System.out.println("Find people who have sued more than 1 time in the last 3 years:");
        Set<String> frequentPlaintiffs = caseRecordService.getFrequentPlaintiffs(records);
        i = 0;
        for (String name : frequentPlaintiffs) {
            if (i == frequentPlaintiffs.size() - 1) {
                System.out.print(name);
            } else {
                System.out.print(name + ", ");
            }
            i++;
        }
    }
}