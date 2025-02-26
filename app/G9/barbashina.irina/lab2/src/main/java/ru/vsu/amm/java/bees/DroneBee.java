package ru.vsu.amm.java.bees;

import ru.vsu.amm.java.enumBees.BeeType;

import java.util.Objects;

public class DroneBee extends Bee {
    private final boolean canFly;
    private final int flightDistance;

    public DroneBee(int age, boolean canFly, int flightDistance) {
        super(BeeType.DRONE, age);
        this.canFly = canFly;
        this.flightDistance = flightDistance;
    }

    public boolean canFly() {
        return canFly;
    }

    public int getFlightDistance() {
        return flightDistance;
    }

    @Override
    public void gatherNectar() {
        System.out.println("Drone bee cannot gather nectar");
    }

    @Override
    public void produceHoney() {
        System.out.println("Drone bee cannot produce honey");
    }

    @Override
    public void defendHive() {
        System.out.println("Drone bee defending the hive");
    }

    @Override
    public void layEggs() {
        System.out.println("Drone bee cannot lay eggs");
    }

    @Override
    public String toString() {
        return super.toString() + ", Can Fly: " + canFly + ", Flight Distance: " + flightDistance;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || getClass() != obj.getClass()) {
            return false;
        }
        DroneBee other = (DroneBee) obj;
        return canFly == other.canFly && flightDistance == other.flightDistance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), canFly, flightDistance);
    }
}
