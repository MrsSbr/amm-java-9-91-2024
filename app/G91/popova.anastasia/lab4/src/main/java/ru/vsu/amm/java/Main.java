package ru.vsu.amm.java;

/*
* Магазин украшений ведет учет продаж:
* Дата;изделие;металл;список камней;стоимость
* Найти:
* Тип изделия, в создании которых использовалось больше всего различных камней
* Месяц, за который продали меньше всего украшений из серебра
* Найти все камни, которые продавались 3 года назад, но не продавались последние полгода
* В задаче должны использоваться элементы функционального программирования
* Задача должна быть покрыта тестами с помощью JUnit
* Должно использоваться логгирование
*/

import ru.vsu.amm.java.analyzer.SalesAnalyzer;
import ru.vsu.amm.java.entity.SaleRecord;
import ru.vsu.amm.java.enums.Gemstone;
import ru.vsu.amm.java.util.FileHandle;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {
    private static final String PATH = "app/G91/popova.anastasia/lab4/salerecs.txt";
    private static final int RECS = 10;
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    static {
        try {
            final FileHandler fh = new FileHandler("app/G91/popova.anastasia/lab4/main.log", true);
            logger.addHandler(fh);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "couldn't initialize file handler for logger", e);
        }
    }


    public static void main(String[] args) {

        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.INFO);
        for (var handler : rootLogger.getHandlers()) {
            handler.setLevel(Level.INFO);
        }

        logger.log(Level.INFO, "starting the app...");
        FileHandle handler = new FileHandle();
        handler.saveToFile(PATH, RECS);
        List<SaleRecord> records = handler.getFromFile(PATH);
        if (records.isEmpty()) {
            logger.log(Level.SEVERE, "no records were found");
            return;
        }

        SalesAnalyzer analyzer = new SalesAnalyzer();

        logger.log(Level.INFO, "analyzing sale records...");
        System.out.print("\njewelry type with most different gemstones use: ");
        try {
            System.out.println(analyzer.findJewelryWithMostGems(records));
        } catch(NoSuchElementException e) {
            System.out.println(e.getMessage());
            logger.log(Level.SEVERE, "couldn't find any elements after calling findJewelryWithMostGems", e);
        }
        System.out.print("\nmonth with least silver sales: ");
        try {
            System.out.println(analyzer.findMonthWithLeastSilverSales(records));
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            logger.log(Level.SEVERE, "couldn't find any elements after calling findMonthWithLeastSilverSales", e);
        }
        System.out.print("\ngems sold 3 years ago but not in the last 6 months: ");
        try {
            Set<Gemstone> gems = analyzer.findGemsBySellingTime(records);
            System.out.println(gems.stream()
                    .map(Enum::name)
                    .collect(Collectors.joining(", ")));
        } catch (NullPointerException | NoSuchElementException e) {
            System.out.println(e.getMessage());
            logger.log(Level.SEVERE, "couldn't find any elements after calling findGemsBySellingTime", e);
        }

        logger.log(Level.INFO, "app finished working");
    }

}