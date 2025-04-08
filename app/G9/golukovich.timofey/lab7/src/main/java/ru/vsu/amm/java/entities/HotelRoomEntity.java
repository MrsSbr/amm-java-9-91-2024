package ru.vsu.amm.java.entities;

public class HotelRoomEntity {
    private int id;
    private int hotelId;
    private int roomNumber;
    private int floorNumber;
    private int bedsCount;
    private String specifications;

    public HotelRoomEntity(int id, int hotelId, int roomNumber, int floorNumber,
                           int bedsCount, String specifications) {
        this.id = id;
        this.hotelId = hotelId;
        this.roomNumber = roomNumber;
        this.floorNumber = floorNumber;
        this.bedsCount = bedsCount;
        this.specifications = specifications;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public int getBedsCount() {
        return bedsCount;
    }

    public void setBedsCount(int bedsCount) {
        this.bedsCount = bedsCount;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }
}
