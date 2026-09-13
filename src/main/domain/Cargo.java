package main.domain;

public class Cargo {
    private String description;
    private double weight;
    private double declaredValue;
    private boolean hazardous;

    public Cargo(String description, double weight, double declaredValue, boolean hazardous) {
        this.description = description;
        this.weight = weight;
        this.declaredValue = declaredValue;
        this.hazardous = hazardous;
    }

    public String getDescription() { return description; }
    public double getWeight() { return weight; }
    public double getDeclaredValue() { return declaredValue; }
    public boolean isHazardous() { return hazardous; }
}
