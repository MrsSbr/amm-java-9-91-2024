package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.RobbedShip;
import ru.vsu.amm.java.enums.Nationality;
import ru.vsu.amm.java.enums.ShipClass;

import java.time.LocalDate;
import java.util.Random;

import static ru.vsu.amm.java.enums.Nationality.BRITAIN;
import static ru.vsu.amm.java.enums.Nationality.PORTUGAL;
import static ru.vsu.amm.java.enums.Nationality.SPAIN;
import static ru.vsu.amm.java.enums.ShipClass.BRIGANTINE;
import static ru.vsu.amm.java.enums.ShipClass.CARAVEL;
import static ru.vsu.amm.java.enums.ShipClass.DRAKKAR;
import static ru.vsu.amm.java.enums.ShipClass.FRIGATE;

public class RobbedShipFactory {

    private static final int DAYS = 7300;

    private static final ShipClass[] SHIP_CLASSES = {
            FRIGATE,
            DRAKKAR,
            BRIGANTINE,
            CARAVEL
    };

    private static final Nationality[] NATIONALITIES = {
            PORTUGAL,
            BRITAIN,
            SPAIN
    };

    public static RobbedShip generateRobbedShip() {
        Random random = new Random();
        LocalDate robbedDate = LocalDate.now().minusDays(random.nextInt(DAYS));
        ShipClass shipClass = SHIP_CLASSES[random.nextInt(SHIP_CLASSES.length)];
        Nationality nationality = NATIONALITIES[random.nextInt(NATIONALITIES.length)];
        int goldCount = random.nextInt(1000);
        int barrelCount = random.nextInt(100);
        boolean isBoarding = random.nextBoolean();
        return RobbedShip.builder()
                .robbedDate(robbedDate)
                .shipClass(shipClass)
                .nationality(nationality)
                .goldCount(goldCount)
                .barrelCount(barrelCount)
                .isBoarding(isBoarding)
                .build();
    }
}
