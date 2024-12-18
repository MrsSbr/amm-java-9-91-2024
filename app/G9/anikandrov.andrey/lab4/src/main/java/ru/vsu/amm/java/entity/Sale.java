package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.Car;
import ru.vsu.amm.java.enums.Showroom;
import ru.vsu.amm.java.enums.Equipment;

import java.time.LocalDate;

import static ru.vsu.amm.java.service.SalesLogger.logger;

public class Sale {
    private LocalDate dateOfSale;
    private Showroom dealCenter;
    private Car car;
    private Equipment equipment;
    private Integer markup;

    public Sale() {
    }

    public Sale(LocalDate dateOfSale, Showroom dealCenterName, Car car, Equipment equipment, Integer markup) {
        this.dateOfSale = dateOfSale;
        this.dealCenter = dealCenterName;
        this.car = car;
        this.equipment = equipment;
        this.markup = markup;

        logger.fine("sale of " + car + " in " + dealCenterName);
    }

    public LocalDate getDateOfSale() {
        return dateOfSale;
    }

    public Showroom getDealCenter() {
        return dealCenter;
    }

    public Car getCar() {
        return car;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public Integer getMarkup() {
        return markup;
    }

    public void setDateOfSale(LocalDate dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public void setDealCenter(Showroom dealCenter) {
        this.dealCenter = dealCenter;
    }

    public void setCarName(Car car) {
        this.car = car;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public void setMarkup(Integer markup) {
        this.markup = markup;
    }

    @Override
    public String toString() {
        return "Sale{" +
                dateOfSale +
                ", " + dealCenter + '\'' +
                ", " + car + '\'' +
                ", " + equipment +
                '}';
    }
}
