package ru.vsu.amm.java;

public class Orchestra {
    public static void main(String[] args) {
        Instrument violin = new StringInstrument("Violin", 350, Material.WOOD, 4);
        Instrument flute = new WindInstrument("Flute", 150, Material.METAL, 60);

        violin.playSound();
        flute.playSound();

        violin.tune();
        flute.tune();

        System.out.println(violin.toString());
        System.out.println(flute.toString());

        Instrument anotherFlute = new WindInstrument("Flute", 150, Material.METAL, 60);
        System.out.println("flute equals anotherFlute: " + flute.equals(anotherFlute));
        System.out.println("flute hashCode: " + flute.hashCode());
        System.out.println("anotherFlute hashCode: " + anotherFlute.hashCode());

        if (violin instanceof StringInstrument) {
            System.out.println(violin.getModel() + " is a String Instrument.");
        } else if (violin instanceof WindInstrument) {
            System.out.println(violin.getModel() + " is a Wind Instrument.");
        }
    }
}
