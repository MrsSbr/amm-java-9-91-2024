package ru.vsu.amm.java.Service.Impl;

import ru.vsu.amm.java.Config.DatabaseConfig;
import ru.vsu.amm.java.Exception.DbException;
import ru.vsu.amm.java.Mapper.Impl.TicketMapper;
import ru.vsu.amm.java.Model.Entity.TicketEntity;
import ru.vsu.amm.java.Model.Enum.TicketStatus;
import ru.vsu.amm.java.Repository.Impl.TicketRepo;
import ru.vsu.amm.java.Service.Interface.TicketService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


public class DefaultTicketServiceImpl implements TicketService {

    private static final String DB_ERROR = "Ошибка бд";

    private static final Logger log = Logger.getLogger(DefaultTicketServiceImpl.class.getName());

    private final TicketRepo ticketRepo;

    public DefaultTicketServiceImpl() {
        this.ticketRepo = new TicketRepo(DatabaseConfig.getDataSource(), new TicketMapper());
    }

    @Override
    public Optional<TicketEntity> getTicketById(Long id) {
        log.info("Попытка получения билета с ID: " + id);
        try {
            Optional<TicketEntity> ticket = ticketRepo.findById(id);
            if (ticket.isPresent()) {
                log.info("Билет с ID " + id + " найден.");
            } else {
                log.warning("Билет с ID " + id + " не найден.");
            }
            return ticket;
        } catch (SQLException e) {
            log.severe("Ошибка при получении билета с ID " + id + ": " + e.getMessage());
            throw new DbException(DB_ERROR);
        }
    }

    @Override
    public List<TicketEntity> getAllTickets() {
        log.info("Попытка получения списка всех билетов.");
        try {
            List<TicketEntity> tickets = ticketRepo.findAll();
            log.info("Список всех билетов получен. Количество билетов: " + tickets.size());
            return tickets;
        } catch (SQLException e) {
            log.severe("Ошибка при получении списка билетов: " + e.getMessage());
            throw new DbException(DB_ERROR);
        }
    }

    @Override
    public void addTicket(long filmId,int hallNumber,int placeNumber,OffsetDateTime startTime,OffsetDateTime endTime, Long userId, String film  ) {

        TicketEntity ticket = new TicketEntity();
        ticket.setFilmId(filmId);
        ticket.setUserId(userId);
        ticket.setHallNumber(hallNumber);
        ticket.setPlaceNumber(placeNumber);
        ticket.setStartTime(startTime);
        ticket.setEndTime(endTime);
        ticket.setStatus(TicketStatus.PAID);
        ticket.setFilmName(film);
        log.info("Попытка добавления билета для фильма с ID: " + ticket.getFilmId() + " пользователем с ID: " + ticket.getUserId());
        try {
            ticketRepo.save(ticket);
            log.info("Билет для фильма с ID " + ticket.getFilmId() + " успешно добавлен.");
        } catch (SQLException e) {
            log.severe("Ошибка при добавлении билета для фильма с ID " + ticket.getFilmId() + ": " + e.getMessage());
            throw new DbException(DB_ERROR);
        }
    }

    @Override
    public void updateTicket(TicketEntity ticket) {
        log.info("Попытка обновления билета с ID: " + ticket.getTicketId());
        try {
            ticketRepo.update(ticket);
            log.info("Билет с ID " + ticket.getTicketId() + " успешно обновлен.");
        } catch (SQLException e) {
            log.severe("Ошибка при обновлении билета с ID " + ticket.getTicketId() + ": " + e.getMessage());
            throw new DbException(DB_ERROR);
        }
    }

    @Override
    public void deleteTicket(Long id) {
        log.info("Попытка удаления билета с ID: " + id);
        try {
            ticketRepo.delete(id);
            log.info("Билет с ID " + id + " успешно удален.");
        } catch (SQLException e) {
            log.severe("Ошибка при удалении билета с ID " + id + ": " + e.getMessage());
            throw new DbException(DB_ERROR);
        }
    }

    @Override
    public List<TicketEntity> getTicketsByUserId(Long userId) {
        log.info("Попытка получения билетов для пользователя с ID: " + userId);
        try {
            List<TicketEntity> tickets = ticketRepo.findByUserId(userId);
            log.info("Найдено " + tickets.size() + " билетов для пользователя с ID " + userId);
            return tickets;
        } catch (SQLException e) {
            log.severe("Ошибка при получении билетов для пользователя с ID " + userId + ": " + e.getMessage());
            throw new DbException(DB_ERROR);
        }
    }
}