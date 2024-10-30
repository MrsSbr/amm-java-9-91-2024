package ru.vsu.amm.java;

import java.util.Objects;

public class WorkerBee extends Bee {
    private int numberOfNectarGathered;
    private int numberOfHoneyProduced;

    public WorkerBee(int age, int numberOfNectarGathered, int numberOfHoneyProduced) {
        super(BeeType.WORKER, age);
        this.numberOfNectarGathered = numberOfNectarGathered;
        this.numberOfHoneyProduced = numberOfHoneyProduced;
    }

    public int getNumberOfNectarGathered() {
        return numberOfNectarGathered;
    }

    public int getNumberOfHoneyProduced() {
        return numberOfHoneyProduced;
    }

    @Override
    public void gatherNectar() {
        System.out.println("Worker bee gathering nectar");
    }

    @Override
    public void produceHoney() {
        System.out.println("Worker bee producing honey");
    }

    @Override
    public void defendHive() {
        System.out.println("Worker bee defending the hive");
    }

    @Override
    public void layEggs() {
        System.out.println("Worker bee cannot lay eggs");
    }

    @Override
    public String toString() {
        return super.toString() + ", Nectar Gathered: " + numberOfNectarGathered + ", Honey Produced: " + numberOfHoneyProduced;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || getClass() != obj.getClass()) {
            return false;
        }
        WorkerBee other = (WorkerBee) obj;
        return numberOfNectarGathered == other.numberOfNectarGathered && numberOfHoneyProduced == other.numberOfHoneyProduced;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numberOfNectarGathered, numberOfHoneyProduced);
    }
}