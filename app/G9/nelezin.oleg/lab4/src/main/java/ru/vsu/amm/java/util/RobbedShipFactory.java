package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.RobbedShip;
import ru.vsu.amm.java.enums.Nationality;
import ru.vsu.amm.java.enums.ShipClass;

import java.util.Random;

import static ru.vsu.amm.java.enums.Nationality.BRITAIN;
import static ru.vsu.amm.java.enums.Nationality.PORTUGAL;
import static ru.vsu.amm.java.enums.Nationality.SPAIN;
import static ru.vsu.amm.java.enums.ShipClass.BRIGANTINE;
import static ru.vsu.amm.java.enums.ShipClass.CARAVEL;
import static ru.vsu.amm.java.enums.ShipClass.DRAKKAR;
import static ru.vsu.amm.java.enums.ShipClass.FRIGATE;

public class RobbedShipFactory {

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

    private static RobbedShip generateRobbedShip() {
        Random random = new Random();
        return RobbedShip.builder().build();
    }
}
