package ru.vsu.amm.java.entity;

import java.sql.Time;

public class receipt {
    private Long id;
    private String dosage;
    private Long personId;
    private Long medicineID;
    private Time appointmentTime;
    private Integer amountToConsume;
    private Integer amountConsumed;
}