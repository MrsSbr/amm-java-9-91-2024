package Entities;

import java.util.Date;

public class Event {
    private long eventID;
    private String name;
    private Date timeBegin;
    private Date timeEnd;
    private Integer peopleCount;
    private Integer price;


    public Event () {}

    public Event(long eventID, String name, Date timeBegin, Date timeEnd, Integer peopleCount, Integer price) {
        this.eventID = eventID;
        this.name = name;
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
        this.peopleCount = peopleCount;
        this.price = price;
    }

    public long getEventID() {
        return eventID;
    }

    public void setEventID(long eventID) {
        this.eventID = eventID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(Integer peopleCount) {
        this.peopleCount = peopleCount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
