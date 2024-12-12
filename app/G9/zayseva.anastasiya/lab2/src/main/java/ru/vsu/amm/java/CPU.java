package ru.vsu.amm.java;
import java.util.Objects;

public class CPU extends HardwareComponent {
    private  int cores;
    private double clockSpeed;

    public CPU(String manufacturer, String model, double price, int cores, double clockSpeed) {
        super(manufacturer, model, price);
        this.cores=cores;
        this.clockSpeed=clockSpeed;
    }

    public String getDescription()
    {
        return "CPU with "+cores+" cores, "+clockSpeed+" GHz";
    }

    public int getCores() {return cores;}

    public double getClockSpeed() {return clockSpeed;}

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CPU cpu = (CPU) o;
        return cores == cpu.cores && Double.compare(cpu.clockSpeed, clockSpeed) == 0;
    }

    @Override
    public int hashCode () {
        return Objects.hash(super.hashCode(), cores, clockSpeed);
    }
}
