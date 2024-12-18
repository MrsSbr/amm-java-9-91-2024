package ru.vsu.amm.java.entities;

import ru.vsu.amm.java.annotations.Table;

import java.time.LocalDate;

@Table(name = "users_table")
public record UserEntity(int id, String userName, String password, LocalDate creationTime) {
}
