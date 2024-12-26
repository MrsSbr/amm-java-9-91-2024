package ru.vsu.amm.java;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static ru.vsu.amm.java.DetailsService.*;

public class PartsController {

    private static final int PARTS_COUNT = 1570;

    public static void main(String[] args) {
        List<Part> list = new LinkedList<>();
        generateList(PARTS_COUNT, list);
        print(list);
        List<String> detailsCount = countDetails(list);
        Set<DetailTypes> detailTypes = getUniqueDetailTypes(list);
        System.out.println("Details types: ");
        for (DetailTypes detailType : detailTypes) {
            System.out.println(detailType);
        }
        System.out.println("Details count: ");
        for (String detail : detailsCount) {
            System.out.println(detail);
        }
    }

}
