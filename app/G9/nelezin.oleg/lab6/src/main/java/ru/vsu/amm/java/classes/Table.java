package ru.vsu.amm.java.classes;

public class Table {

    private String firstComponent = null;

    private String secondComponent = null;

    public synchronized void placeComponents(String component1, String component2) {
        while (firstComponent != null || secondComponent != null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        this.firstComponent = component1;
        this.secondComponent = component2;
        System.out.println("Бармен положил " + component1 + ", " + component2);
        notifyAll();
    }

    public synchronized void takeComponents(String needComponent) {
        while (firstComponent == null || secondComponent == null
                || firstComponent.equals(needComponent) || secondComponent.equals(needComponent)) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Курильщик с " + needComponent + " забирает компоненты");
        firstComponent = null;
        secondComponent = null;
        notifyAll();
    }
}
