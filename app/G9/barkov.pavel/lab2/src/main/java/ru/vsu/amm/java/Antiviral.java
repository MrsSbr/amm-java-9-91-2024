package src.main.java.ru.vsu.amm.java;

public class Antiviral extends Medicine implements Medicinal {

    private String contraindication;

    public Antiviral(String name, int volume, String contraindication) {
        super(name, volume);
        this.contraindication = contraindication;
    }

    public void heal() {
        System.out.println("От вирусных заболеваний");
    }

    public String toString() {
        return super.toString() + "\nПротивопоказания:" + contraindication;
    }

    public boolean equals(Object obj) {
        Antiviral that = (Antiviral) obj;
        return super.equals(obj) && that.contraindication.equals(contraindication);
    }
}
