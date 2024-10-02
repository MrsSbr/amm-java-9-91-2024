package ru.vsu.amm.java.Config;

import java.time.LocalDate;

public class PatientConfig {

    public static final int COUNT = 150;


    public static final int NAMELENGTH = 5;

    public static final int SURNAMELENGTH = 10;

    public static final int PATRONYMICLENGTH = 5;

    public static final int ILLNESSLENGTH = 20;


    public static final LocalDate NOW = LocalDate.now();
    public static final LocalDate FIRSTDATE = LocalDate.now().minusYears(10);

    public static final LocalDate HEALTHYPERIOD = LocalDate.now().minusYears(1);

    public static final LocalDate TASK2PERIOD = LocalDate.now().minusYears(3);

    public  static final LocalDate TASK3AFTER = LocalDate.now().minusYears(5);

    public  static final LocalDate TASK3BEFORE = LocalDate.now().minusYears(2);
}

