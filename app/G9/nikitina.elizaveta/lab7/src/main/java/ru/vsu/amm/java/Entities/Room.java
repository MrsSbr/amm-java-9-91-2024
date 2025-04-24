package ru.vsu.amm.java.Entities;

import ru.vsu.amm.java.Enums.RoomType;

public class Room {
    private int roomId;
    private int hotelId;
    private RoomType roomType;
    private double pricePerNight;
    private int capacity;
    private String description;

    // Конструкторы
    public Room() {}

    public Room(int hotelId, RoomType roomType, double pricePerNight,
                int capacity, String description) {
        this.hotelId = hotelId;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.capacity = capacity;
        this.description = description;
    }

    // Геттеры и сеттеры
    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }

    public RoomType getRoomType() { return roomType; }
    public void setRoomType(RoomType roomType) { this.roomType = roomType; }

    public double getPricePerNight() { return pricePerNight; }
    public void setPricePerNight(double pricePerNight) { this.pricePerNight = pricePerNight; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

}