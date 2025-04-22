package ru.vsu.amm.java.Entities;

import java.sql.Date;
import java.time.LocalDate;

public class AgreementEntity {
    private Long agreementID;
    private Long userID;
    private Long objectID;
    private LocalDate timeStart;
    private LocalDate timeEnd;
    private Integer sumPrice;


    public AgreementEntity() {}

    public AgreementEntity(Long agreementID, Long userID, Long objectID, LocalDate timeStart, LocalDate timeEnd, Integer sumPrice) {
        this.agreementID = agreementID;
        this.userID = userID;
        this.objectID = objectID;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.sumPrice = sumPrice;
    }

    public Long getAgreementID() {
        return agreementID;
    }

    public void setAgreementID(Long agreementID) {
        this.agreementID = agreementID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getObjectID() {
        return objectID;
    }

    public void setObjectID(Long objectID) {
        this.objectID = objectID;
    }

    public LocalDate getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalDate timeStart) {
        if (timeEnd != null && timeStart != null && timeStart.isAfter(timeEnd)) {
            throw new IllegalArgumentException("setTimeBegin Error");
        }
        this.timeStart = timeStart;
    }

    public LocalDate getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalDate timeEnd) {
        if (timeStart != null && timeEnd != null && timeEnd.isBefore(timeStart)) {
            throw new IllegalArgumentException("setTimeEnd Error");
        }
        this.timeEnd = timeEnd;
    }

    public Integer getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(Integer sumPrice) {
        this.sumPrice = sumPrice;
    }
}
