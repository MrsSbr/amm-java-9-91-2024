package ru.vsu.amm.java.Service.Interface;

import ru.vsu.amm.java.Model.Entity.TicketEntity;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface TicketService {

    Optional<TicketEntity> getTicketById(Long id);

    List<TicketEntity> getAllTickets();

    List<TicketEntity> getTicketsByUserId(Long userId);  // New method

    void addTicket(long filmId, int hallNumber, int placeNumber, OffsetDateTime startTime, OffsetDateTime endTime, Long userId, String film);

    void updateTicket(TicketEntity ticket);

    void deleteTicket(Long id);
}