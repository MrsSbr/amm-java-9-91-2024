package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.Sale;
import ru.vsu.amm.java.service.SalesService;
import ru.vsu.amm.java.util.SalesGenerator;

import java.util.ArrayList;
import java.util.List;

public class Task {

    public static void main(String[] args) {

        List<Sale> sales = SalesGenerator.generateSale(50);

        System.out.println("1. Max markup on this car: ");
        System.out.println(SalesService.findMaxMarkup(sales));

        System.out.println("2. Showroom with max auto equipments: ");
        System.out.println(SalesService.findBestShowroom(sales));

        System.out.println("3. Total markup for each showroom");
        System.out.println(SalesService.findTotalMarkup(sales));
    }
}
