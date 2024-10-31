package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.abstracts.AbstractCosmetic;
import ru.vsu.amm.java.enums.Brand;
import ru.vsu.amm.java.enums.Texture;

import java.util.Objects;

public class Sculptor extends AbstractCosmetic {

    private Texture texture;

    public Sculptor(Brand brand, double price, Texture texture){
        super(brand, price);
        this.texture=texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Texture getTexture(){
        return texture;
    }

    @Override
    public String toString(){
        return super.toString() + " Texture: " + texture;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Sculptor that = (Sculptor) obj;
        return getTexture().equals(that.getTexture());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), texture);
    }


}
