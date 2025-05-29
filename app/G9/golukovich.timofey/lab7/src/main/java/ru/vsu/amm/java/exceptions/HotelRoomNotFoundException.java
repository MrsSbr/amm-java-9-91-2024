package ru.vsu.amm.java.exceptions;

public class HotelRoomNotFoundException extends RuntimeException {
    public HotelRoomNotFoundException(String message) {
        super(message);
    }
}