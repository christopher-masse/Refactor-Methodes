package main.domain;

public class Model {
    private String description;
    private int capacity;

    public Model(String description, int capacity) {
        this.description = description;
        this.capacity = capacity;
    }

    public double getCapacity() { return capacity; }

}
