package src.main.java.ru.vsu.amm.java;

public class Homeopathy extends Medicine {
    private String obscurantism;

    public Homeopathy(String name, int volume, String obscurantism) {
        super(name, volume);
        this.obscurantism = obscurantism;
    }

    public  String getObscurantism(){
        return obscurantism;
    }

    public String toString() {
        return super.toString() + "\nОбоснование теории:" + obscurantism;
    }

    public boolean equals(Object obj) {
        Homeopathy that = (Homeopathy) obj;
        return super.equals(obj) && that.obscurantism.equals(obscurantism);
    }
}
