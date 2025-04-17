package ru.vsu.amm.java.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.vsu.amm.java.enums.CarClass;
import ru.vsu.amm.java.enums.Status;

@Data
@AllArgsConstructor
public class CarDto {
    private Long id;
    private String manufacturer;
    private String model;
    private int year;
    private Status status;
    private CarClass carClass;
}

