package ru.vsu.amm.java.Dtos;

import ru.vsu.amm.java.Enums.Status;

import java.time.LocalDate;

public record BookingDto(
        int id,
        int idTour,
        int idUser,
        LocalDate date,
        int countParticipants,
        int totalPrice,
        Status status) {
}
