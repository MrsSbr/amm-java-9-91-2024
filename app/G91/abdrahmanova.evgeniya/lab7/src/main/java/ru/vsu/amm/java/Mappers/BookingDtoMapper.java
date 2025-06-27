package ru.vsu.amm.java.Mappers;

import ru.vsu.amm.java.Dtos.BookingDto;
import ru.vsu.amm.java.Entities.Booking;

public class BookingDtoMapper {
    public static BookingDto mapFromEntity(Booking e) {
        return new BookingDto(e.getId(), e.getIdTour(), e.getIdUser(), e.getDate(),
                e.getCountParticipants(), e.getTotalPrice(), e.getStatus());
    }
}
