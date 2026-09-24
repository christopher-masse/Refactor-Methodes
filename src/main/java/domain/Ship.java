package main.java.domain;

public class Ship {
    private final String registration;
    private final String model;
    private final double capacity;
    private int permissions;

    public Ship(String registration, String model, double capacity, int permissions) {
        this.registration = registration;
        this.model = model;
        this.capacity = capacity;
        this.permissions = permissions;
    }

    public String getRegistration() { return registration; }
    public String getModel() { return model; }
    public double getCapacity() { return capacity; }
    public int getPermissions() { return permissions; }
    public void setPermissions(int permissions) { this.permissions = permissions; }
}
