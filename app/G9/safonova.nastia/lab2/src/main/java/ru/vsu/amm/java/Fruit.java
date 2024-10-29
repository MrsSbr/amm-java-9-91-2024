package ru.vsu.amm.java;
import java.util.Objects;
public class Fruit extends  Plant implements Edible{

    private String sweetness;
    private int cntFruit;

    public Fruit(String name, String sweetness, int cntFruit){
        super(name);
        this.sweetness = sweetness;
        this.cntFruit = cntFruit;
    }

    @Override
    public void grow(){
        System.out.println(getName() + " fruit is growing");
    }

    @Override
    public boolean isEdible(){
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        Fruit that = (Fruit) obj;
        if (obj == null)
            return false;
        else
            return sweetness == that.sweetness
                    && cntFruit == that.cntFruit
                    && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sweetness, cntFruit);
    }

}
