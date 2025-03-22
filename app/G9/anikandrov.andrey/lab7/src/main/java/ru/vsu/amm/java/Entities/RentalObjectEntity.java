package ru.vsu.amm.java.Entities;

import ru.vsu.amm.java.Enums.ObjectType;


public class RentalObjectEntity {
    private Long objectID;
    private String objectName;
    private ObjectType objectType;
    private String objectInfo;
    private Integer price;


    public RentalObjectEntity() {}

    public RentalObjectEntity(Long objectID, String objectName, ObjectType objectType, String objectInfo, Integer price) {
        this.objectID = objectID;
        this.objectName = objectName;
        this.objectType = objectType;
        this.objectInfo = objectInfo;
        this.price = price;
    }

    public Long getObjectID() {
        return objectID;
    }

    public void setObjectID(Long objectID) {
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

    public String getObjectInfo() {
        return objectInfo;
    }

    public void setObjectInfo(String objectInfo) {
        this.objectInfo = objectInfo;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
