package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.vsu.amm.java.enums.ScooterStatus;

@Data
@AllArgsConstructor
public class ScooterEntity {
    private Long id;
    private String model;
    private Double latitude;
    private Double longitude;
    private ScooterStatus status;
}
