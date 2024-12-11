package ru.vsu.amm.java;

import java.util.Objects;

public class Plant {
    private String name;

    public Plant(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public void grow(){
        System.out.println(name + " is growing.");
    }

    @Override
    public boolean equals(Object obj) {
        Plant that = (Plant) obj;
        if (obj == null)
            return false;
        else
            return name.equals(that.name) && super.equals(obj);
           // return name == that.name && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
