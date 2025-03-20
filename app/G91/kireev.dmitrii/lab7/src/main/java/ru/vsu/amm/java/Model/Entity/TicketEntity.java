package ru.vsu.amm.java.Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.vsu.amm.java.Model.Enum.TicketStatus;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class TicketEntity {


    private long ticketId;
    private TicketStatus status;
    private int hallNumber;
    private int placeNumber;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;

    private long userId;
    private long filmId;


}
