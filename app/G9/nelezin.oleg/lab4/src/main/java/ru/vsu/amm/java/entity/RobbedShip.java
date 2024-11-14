package ru.vsu.amm.java.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.vsu.amm.java.enums.Nationality;
import ru.vsu.amm.java.enums.ShipClass;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class RobbedShip {

    private LocalDate robbedDate;

    private ShipClass shipClass;

    private Nationality nationality;

    private int goldCount;

    private int barrelCount;

    private boolean isBoarding;
}
