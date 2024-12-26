package ru.vsu.amm.java;

import ru.vsu.amm.java.constants.Constants;
import ru.vsu.amm.java.entity.Sale;
import ru.vsu.amm.java.service.SalesLogger;
import ru.vsu.amm.java.service.SalesService;
import ru.vsu.amm.java.util.FileWorker;
import ru.vsu.amm.java.util.SalesGenerator;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Task {

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Task.class.getName());

        try {
            // List<Sale> sales = SalesGenerator.generateSale(Constants.SALES_COUNT);
            FileWorker.generateFile(Constants.FILE_PATH, Constants.SALES_COUNT);
            List<Sale> sales = FileWorker.getFromFile(Constants.FILE_PATH);

            System.out.println("1. Where max markup on each car: ");
            System.out.println(SalesService.findMaxMarkup(sales));

            System.out.println("2. Showroom with max auto equipments: ");
            System.out.println(SalesService.findBestShowroom(sales));

            System.out.println("3. Total markup for each showroom");
            System.out.println(SalesService.findTotalMarkup(sales));
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, e.toString());
            System.out.println(e.toString());
        }
    }
}
