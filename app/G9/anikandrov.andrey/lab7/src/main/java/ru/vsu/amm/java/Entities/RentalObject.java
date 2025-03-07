package ru.vsu.amm.java.Entities;

import ru.vsu.amm.java.Enums.ObjectType;

import java.util.Date;

public class RentalObject {
    private long objectID;
    private String objectName;
    private ObjectType objectType;
    private String info;
    private Integer price;


    public RentalObject() {}

    public RentalObject(long objectID, String objectName, ObjectType objectType, String info, Integer price) {
        this.objectID = objectID;
        this.objectName = objectName;
        this.objectType = objectType;
        this.info = info;
        this.price = price;
    }

    public long getObjectID() {
        return objectID;
    }

    public void setObjectID(long objectID) {
        this.objectID = objectID;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
