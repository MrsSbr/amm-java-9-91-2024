package ru.vsu.amm.java.Entities;

import java.util.Date;

public class Agreement {
    private long agreementID;
    private long userID;
    private long objectID;
    private Date timeBegin;
    private Date timeEnd;
    private Integer sumPrice;


    public Agreement () {}

    public Agreement(long agreementID, long userID, long objectID, Date timeBegin, Date timeEnd, Integer sumPrice) {
        this.agreementID = agreementID;
        this.userID = userID;
        this.objectID = objectID;
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
        this.sumPrice = sumPrice;
    }

    public long getAgreementID() {
        return agreementID;
    }

    public void setAgreementID(long agreementID) {
        this.agreementID = agreementID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getObjectID() {
        return objectID;
    }

    public void setObjectID(long objectID) {
        this.objectID = objectID;
    }

    public Date getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(Date timeBegin) {
        if (timeEnd != null && timeBegin != null && timeBegin.after(timeEnd)) {
            throw new IllegalArgumentException("setTimeBegin Error");
        }
        this.timeBegin = timeBegin;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        if (timeBegin != null && timeEnd != null && timeEnd.before(timeBegin)) {
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
