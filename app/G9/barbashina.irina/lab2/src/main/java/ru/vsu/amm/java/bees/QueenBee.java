package ru.vsu.amm.java.bees;

import ru.vsu.amm.java.enumBees.BeeType;

import java.util.Objects;

public class QueenBee extends Bee {
    private int numberOfEggsLaid;
    private int numberOfDronesProduced;

    public QueenBee(int age, int numberOfEggsLaid, int numberOfDronesProduced) {
        super(BeeType.QUEEN, age);
        this.numberOfEggsLaid = numberOfEggsLaid;
        this.numberOfDronesProduced = numberOfDronesProduced;
    }

    public int getNumberOfEggsLaid() {
        return numberOfEggsLaid;
    }

    public int getNumberOfDronesProduced() {
        return numberOfDronesProduced;
    }

    @Override
    public void gatherNectar() {
        System.out.println("Queen bee gathering nectar");
    }

    @Override
    public void produceHoney() {
        System.out.println("Queen bee producing honey");
    }

    @Override
    public void defendHive() {
        System.out.println("Queen bee defending the hive");
    }

    @Override
    public void layEggs() {
        System.out.println("Queen bee laying eggs");
    }

    @Override
    public String toString() {
        return super.toString() + ", Eggs Laid: " + numberOfEggsLaid + ", Drones Produced: " + numberOfDronesProduced;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || getClass() != obj.getClass()) {
            return false;
        }
        QueenBee other = (QueenBee) obj;
        return numberOfEggsLaid == other.numberOfEggsLaid && numberOfDronesProduced == other.numberOfDronesProduced;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numberOfEggsLaid, numberOfDronesProduced);
    }
}