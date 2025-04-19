package ru.vsu.amm.java.Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.amm.java.Model.Enum.TicketStatus;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketEntity {


    private Long ticketId;
    private TicketStatus status;
    private int hallNumber;
    private int placeNumber;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;

    private long userId;
    private long filmId;


}
