public class Employee {
    private long idEmpl;
    private String surnameEmpl;
    private String nameEmpl;
    private String patronumicEmpl;
    private LocalDate dateOfBirthEmpl;

    public Employee () {}

    public Employee(long idEmpl, String surnameEmpl, String nameEmpl, LocalDate dateOfBirthEmpl, String patronumicEmpl) {
        this.idEmpl = idEmpl;
        this.surnameEmpl = surnameEmpl;
        this.nameEmpl = nameEmpl;
        this.dateOfBirthEmpl = dateOfBirthEmpl;
        this.patronumicEmpl = patronumicEmpl;
    }

    public long getIdEmpl() {
        return idEmpl;
    }

    public void setIdEmpl(long idEmpl) {
        this.idEmpl = idEmpl;
    }

    public String getSurnameEmpl() {
        return surnameEmpl;
    }

    public void setSurnameEmpl(String surnameEmpl) {
        this.surnameEmpl = surnameEmpl;
    }

    public String getNameEmpl() {
        return nameEmpl;
    }

    public void setNameEmpl(String nameEmpl) {
        this.nameEmpl = nameEmpl;
    }

    public String getPatronumicEmpl() {
        return patronumicEmpl;
    }

    public void setPatronumicEmpl(String patronumicEmpl) {
        this.patronumicEmpl = patronumicEmpl;
    }

    public LocalDate getDateOfBirthEmpl() {
        return dateOfBirthEmpl;
    }

    public void setDateOfBirthEmpl(LocalDate dateOfBirthEmpl) {
        this.dateOfBirthEmpl = dateOfBirthEmpl;
    }
}