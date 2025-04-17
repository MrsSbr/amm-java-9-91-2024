package ru.vsu.amm.java.Entities;

import java.time.LocalDate;

public class Incident {
    private long idIncident;
    private long emplId;
    private long dinoId;
    private LocalDate dateOfIncident;
    private String discription;

    public Incident() {}

    public Incident(long idIncident, long emplId, long dinoId, LocalDate dateOfIncident, String discription) {
        this.idIncident = idIncident;
        this.emplId = emplId;
        this.dinoId = dinoId;
        this.dateOfIncident = dateOfIncident;
        this.discription = discription;
    }

    public long getIdIncident() {
        return idIncident;
    }

    public void setIdIncident(long idIncident) {
        this.idIncident = idIncident;
    }

    public long getEmplId() {
        return emplId;
    }

    public void setEmplId(long emplId) {
        this.emplId = emplId;
    }

    public long getDinoId() {
        return dinoId;
    }

    public void setDinoId(long dinoId) {
        this.dinoId = dinoId;
    }

    public LocalDate getDateOfIncident() {
        return dateOfIncident;
    }

    public void setDateOfIncident(LocalDate dateOfIncident) {
        this.dateOfIncident = dateOfIncident;
    }

    public void setDiscription(String discription){this.discription = discription;}

    public String getDiscription(){return discription;}
}
