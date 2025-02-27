package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScooterEntity {
    private Long id;
    private String location;
    private String model;
}
