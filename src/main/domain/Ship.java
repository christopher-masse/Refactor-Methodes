package main.domain;

public class Ship {
    private Registration registration;
    private Model model;

    public Ship(Registration registration, Model model) {
        this.model = model;
        this.registration = registration;
    }

    public Registration getRegistration() { return registration; }
    public Model getModel() { return model; }

}
