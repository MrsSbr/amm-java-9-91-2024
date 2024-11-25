package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;

public class PartsController {

    private static final int PARTS_COUNT = 1570;

    public static void main(String[] args) {
        DetailsList list = new DetailsList(PARTS_COUNT);
        //list.print();
        List<String> detailsCount = list.countDetails();
        List<DetailTypes> detailTypes = list.getUniqueDetailTypes();
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
