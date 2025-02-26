package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.vsu.amm.java.enums.Status;

@Data
@AllArgsConstructor
public class CarEntity {
    private Long id;
    private String make;
    private String model;
    private int year;
    private Status status;
}
