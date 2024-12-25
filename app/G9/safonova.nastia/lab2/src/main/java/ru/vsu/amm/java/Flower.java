package ru.vsu.amm.java;
import java.util.Objects;
public class Flower extends  Plant implements Edible{

    private int petalCount;

    public Flower(String name, int petalCount){
        super(name);
        this.petalCount = petalCount;
    }

    @Override
    public void grow(){
        System.out.println(getName() + " flower is growing.");
    }
    @Override
    public boolean isEdible(){
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        Flower that = (Flower) obj;
        if (obj == null)
            return false;
        else
            return petalCount == that.petalCount
                    && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), petalCount);
    }
}
