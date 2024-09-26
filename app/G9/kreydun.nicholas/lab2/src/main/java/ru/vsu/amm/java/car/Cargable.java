package ru.vsu.amm.java.car;

public interface Cargable {
    double getVolume(); // Получение объема груза
    String getDescription(); // Получение описания груза
    boolean isFragile(); // Проверка, хрупкий ли груз
    double calculateShippingCost(double ratePerUnit); // Расчет стоимости доставки
}
