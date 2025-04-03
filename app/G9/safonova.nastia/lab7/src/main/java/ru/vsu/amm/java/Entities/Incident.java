public class Incident {
    private long idIncident;
    private long emplId;
    private long dinoId;
    private LocalDate dateOfIncident;

    private String discription;

    public Incident() {}

    public Incident(LocalDate dateOfIncident, long dinoId, long emplId, long idIncident, String discription) {
        this.dateOfIncident = dateOfIncident;
        this.discription = discription;
        this.dinoId = dinoId;
        this.emplId = emplId;
        this.idIncident = idIncident;
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
