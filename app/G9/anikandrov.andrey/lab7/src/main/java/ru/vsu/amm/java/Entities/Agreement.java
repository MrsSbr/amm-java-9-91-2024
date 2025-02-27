package ru.vsu.amm.java.Entities;

import java.util.Date;

public class Agreement {
    private long agreementID;
    private Date timeBegin;
    private Date timeEnd;
    private Integer sumPrice;


    public Agreement () {}

    public Agreement(long agreementID, Date timeBegin, Date timeEnd, Integer sumPrice) {
        this.agreementID = agreementID;
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

    public Date getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(Date timeBegin) {
        this.timeBegin = timeBegin;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Integer getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(Integer sumPrice) {
        this.sumPrice = sumPrice;
    }
}
