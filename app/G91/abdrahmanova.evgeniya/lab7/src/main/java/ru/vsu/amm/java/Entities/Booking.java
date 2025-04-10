package ru.vsu.amm.java.Entities;

import java.time.LocalDate;

public class Booking {
    private int id;
    private int idTour;
    private int idUser;
    private LocalDate date;
    private int countParticipants;
    private int totalPrice;
    private String status;

    public Booking(int id, int idTour, int idUser, LocalDate date, int countParticipants, int totalPrice, String status) {
        this.id = id;
        this.idTour = idTour;
        this.idUser = idUser;
        this.date = date;
        this.countParticipants = countParticipants;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdTour() { return idTour; }
    public void setIdTour(int idTour) { this.idTour = idTour; }

    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public int getCountParticipants() { return countParticipants; }
    public void setCountParticipants(int countParticipants) { this.countParticipants = countParticipants; }

    public int getTotalPrice() { return totalPrice; }
    public void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
