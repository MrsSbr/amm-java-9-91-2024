package ru.vsu.amm.java.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.vsu.amm.java.enums.ShipClass;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class RobbedShip {

    private LocalDate robbedDate;

    private ShipClass shipClass;

    private String nationality;

    private int goldCount;

    private int barrelCount;

    private boolean isBoarding;
}
