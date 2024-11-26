package src.main.java.ru.vsu.amm.java;

public abstract class Medicine {
    private String name;

    private int volume;

    public Medicine(String name, int volume) {
        this.name = name;
        this.volume = volume;
    }

    public String getName() {
        return name;
    }

    public int getVolume() {
        return volume;
    }

    public Medicine() {};

    @Override
    public String toString() {
        return "lecarstvo:" + name + " " + volume + "ml";
    }


    public int hashCode() {
        return (name).hashCode() + volume;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof Medicine)) {
            return false;
        }
        Medicine other = (Medicine) obj;
        return name.equals(other.name) && volume == other.volume;
    }
}
