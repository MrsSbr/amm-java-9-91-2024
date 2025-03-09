package ru.vsu.amm.java.Entities;

import java.io.Serial;
import java.time.LocalDate;

public class AgreementEntity {
    private Serial agreementID;
    private Serial userID;
    private Serial objectID;
    private LocalDate timeStart;
    private LocalDate timeEnd;
    private Integer sumPrice;


    public AgreementEntity() {}

    public AgreementEntity(Serial agreementID, Serial userID, Serial objectID, LocalDate timeStart, LocalDate timeEnd, Integer sumPrice) {
        this.agreementID = agreementID;
        this.userID = userID;
        this.objectID = objectID;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.sumPrice = sumPrice;
    }

    public Serial getAgreementID() {
        return agreementID;
    }

    public void setAgreementID(Serial agreementID) {
        this.agreementID = agreementID;
    }

    public Serial getUserID() {
        return userID;
    }

    public void setUserID(Serial userID) {
        this.userID = userID;
    }

    public Serial getObjectID() {
        return objectID;
    }

    public void setObjectID(Serial objectID) {
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
