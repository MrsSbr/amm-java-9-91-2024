import java.util.Objects;

public class LiquidItem extends ItemImplementation {
    private int volume;

    public LiquidItem(String name,
                      int price,
                      int quantity,
                      int volume) {
        super(name, price, quantity);
        this.volume = volume;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }


    @Override
    public void perform() {
        System.out.println("Бутылка " + getName() + "объемом" + getVolume());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LiquidItem that = (LiquidItem) o;
        return volume == that.volume;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), volume);
    }

    @Override
    public String toString() {
        return "Бутылка: " +
                super.toString() +
                "Объем=" + volume + " мл.";
    }
}