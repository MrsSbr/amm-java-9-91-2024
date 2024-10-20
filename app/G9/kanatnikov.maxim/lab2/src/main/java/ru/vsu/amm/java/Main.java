package ru.vsu.amm.java;

import ru.vsu.amm.java.Classes.CourierDelivery;
import ru.vsu.amm.java.Classes.PostalDelivery;
import ru.vsu.amm.java.abstractClasses.Delivery;

public class Main {
    public static void main(String[] args) {
        Delivery courierDelivery = new CourierDelivery(
                "Университетская 1",
                3000.0,
                10,
                "Петров Петр"
        );

        System.out.println(courierDelivery);
        System.out.println("--------------");
        courierDelivery.deliver();
        System.out.println("--------------");
        System.out.println("courierDelivery.hashCode ="
        + courierDelivery.hashCode());
        System.out.println("--------------");

        Delivery postalDelivery = new PostalDelivery(
                "Остужева 9",
                1000.0,
                4,
                "Переверткина 11"
        );

        System.out.println(postalDelivery);
        System.out.println("--------------");
        postalDelivery.deliver();
        System.out.println("--------------");
        System.out.println("postalDelivery.hashCode ="
                + postalDelivery.hashCode());
        System.out.println("--------------");
        System.out.println("courierDelivery = postalDelivery is "
                + postalDelivery.equals(courierDelivery));
        System.out.println("--------------");

        CourierDelivery anotherCourierDelivery = new CourierDelivery(
                "Университетская 1",
                3000.0,
                10,
                "Петров Петр"
        );

        System.out.println("courierDelivery = anotherCourierDelivery is "
                + courierDelivery.equals(anotherCourierDelivery));
        System.out.println("--------------");

        postalDelivery = anotherCourierDelivery;

        System.out.println("courierDelivery = postalDelivery is "
                + postalDelivery.equals(courierDelivery));
        System.out.println("--------------");
        System.out.println("postalDelivery.hashCode ="
                + postalDelivery.hashCode());
    }
}