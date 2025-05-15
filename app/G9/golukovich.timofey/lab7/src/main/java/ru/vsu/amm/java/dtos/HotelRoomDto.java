package ru.vsu.amm.java.dtos;

public class HotelRoomDto {
    private Integer id;
    private Integer hotelId;
    private Integer roomNumber;
    private Integer floorNumber;
    private Integer bedsCount;
    private String specifications;

    public HotelRoomDto(Integer id, Integer hotelId, Integer roomNumber, Integer floorNumber,
                        Integer bedsCount, String specifications) {
        this.id = id;
        this.hotelId = hotelId;
        this.roomNumber = roomNumber;
        this.floorNumber = floorNumber;
        this.bedsCount = bedsCount;
        this.specifications = specifications;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Integer getBedsCount() {
        return bedsCount;
    }

    public void setBedsCount(Integer bedsCount) {
        this.bedsCount = bedsCount;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }
}
