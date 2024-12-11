package ru.vsu.amm.java;

//Написать генератор sql запросов для класса-сущности.
//Должна поддерживаться генерация запроса апдейта и поиска для любого поля сущности

import ru.vsu.amm.java.entity.TeaBag;
import ru.vsu.amm.java.generator.SqlGenerator;

public class Main {
    public static void main(String[] args) {
        TeaBag teaBag = new TeaBag(1, "green", 2024, 100);
        System.out.println(SqlGenerator.update(teaBag));
        System.out.println(SqlGenerator.select());
    }
}