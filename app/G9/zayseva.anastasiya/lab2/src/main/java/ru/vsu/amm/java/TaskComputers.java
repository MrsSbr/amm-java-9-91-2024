package ru.vsu.amm.java;

public class TaskComputers {
    public static void main(String[] args) {
        CPU cpu=new CPU("Intel", "i9-13900K", 500, 24, 5.8);
        GPU gpu=new GPU("Nvidia", "RTX 4090", 1600, 24);
        RAM ram=new RAM("Corsair", "Dominator", 200, 64);

        HardwareComponent[] components={cpu, gpu, ram};
        for(HardwareComponent component:components){
            System.out.println(component);
            System.out.println(component.getDescription());
            System.out.println("Is this a CPU? "+(component instanceof CPU));
            System.out.println("------------");
        }
    }
}