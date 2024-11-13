package ru.vsu.amm.java;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public final class Confectionery {

    private final String name;
    private final Set<Worker> workers;

    public Confectionery(String name) {
        this.name = name;
        workers = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public Set<Worker> getWorkers() {
        return workers;
    }

    public void hireWorker(Worker worker) {
        workers.add(worker);
    }

    @Override
    public boolean equals(Object object) {
        if(object != null) {
            Confectionery temp = (Confectionery) object;
            return this.getName().equals(temp.getName())
                    && getWorkers().equals(temp.getWorkers());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, workers);
    }

    @Override
    public String toString() {
        StringBuilder workersString = new StringBuilder();
        for(Worker worker: workers) {
            workersString.append(worker.toString()).append("\n\n");
        }
        return "Confectionery: " + this.name + "\n"
                + "Workers: " + "\n" + workersString;
    }
}
