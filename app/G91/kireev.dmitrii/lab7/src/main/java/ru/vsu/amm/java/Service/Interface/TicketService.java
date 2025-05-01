package ru.vsu.amm.java.Service.Interface;

import ru.vsu.amm.java.Model.Entity.TicketEntity;

import java.util.List;
import java.util.Optional;

public interface TicketService {

    Optional<TicketEntity> getTicketById(Long id);

    List<TicketEntity> getAllTickets();

    void addTicket(TicketEntity ticket);

    void updateTicket(TicketEntity ticket);

    void deleteTicket(Long id);
}
