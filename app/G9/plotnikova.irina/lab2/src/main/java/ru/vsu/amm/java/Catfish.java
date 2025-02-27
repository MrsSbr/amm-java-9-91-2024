package ru.vsu.amm.java;

public class Catfish extends  Fish{
    public Catfish(String name, double weight) {
        super(name, weight);
    }

    @Override
    public void swim() {
        System.out.println( name + " плывет  в  Мексиканском заливе ");
    }

    @Override
    public void eat() {
        System.out.println(name + " ищет пищу в виде  мелкой рыбки на дне водоема");
    }

    @Override
    public String getDescription() {
        return getName() + " - сом, который любит охоту на дне";
    }
}
