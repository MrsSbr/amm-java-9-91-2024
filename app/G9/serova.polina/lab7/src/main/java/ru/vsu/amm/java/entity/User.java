package ru.vsu.amm.java.entity;

import java.time.LocalDate;
import java.util.List;

public class User {
    private long id;
    private String name;
    private String surname;
    private String patronymic;
    private LocalDate birthday;
    private String email;
    private String passwordHash;
    private int roleId;
    private Role role;
    private List<Plan> plansAsDoctor;
    private List<Plan> plansAsPatient;
}
