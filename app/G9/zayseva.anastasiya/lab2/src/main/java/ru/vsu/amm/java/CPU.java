package ru.vsu.amm.java;

public class CPU extends HardwareComponent
{
    private  int cores;
    private double clockSpeed;

    public CPU(String manufacturer, String model, double price, int cores, double clockSpeed)
    {
        super(manufacturer, model, price);
        this.cores=cores;
        this.clockSpeed=clockSpeed;
    }

    @Override
    public String getDescription()
    {
        return "CPU with "+cores+" cores, "+clockSpeed+" GHz";
    }

    @Override
    public String getName()
    {
        return "High-Perfomance CPU"+ super.getName();
    }
}
