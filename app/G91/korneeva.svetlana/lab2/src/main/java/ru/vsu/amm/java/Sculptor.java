package ru.vsu.amm.java;

import java.util.Objects;

public class Sculptor extends AbstractCosmetic {

    protected String texture;

    public Sculptor(String brand, double price, String texture){
        super(brand, price);
        this.texture=texture;
    }

    public String getTexture(){
        return texture;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        Sculptor that = (Sculptor) obj;
        return getTexture().equals(that.getTexture());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), texture);
    }

    @Override
    public String toString(){
        return super.toString() + " Texture : " + texture;
    }
}
