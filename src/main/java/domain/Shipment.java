package main.java.domain;

import main.java.enums.ShipmentPriority;
import main.java.enums.ShipmentStatus;
import main.java.exception.CargoException;
import main.java.exception.CustomerException;
import main.java.service.ManifestRepository;
import main.java.service.PermissionService;
import main.java.service.PricingService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Shipment {
    private final int PRIORITY_AMOUNT = 2000;

    private final String reference;
    private final Customer customer;
    private final Planet origin;
    private final Planet destination;
    private final Ship ship;
    private final LocalDate departureDate;
    private final List<Cargo> cargo = new ArrayList<>();
    private double total;
    private ShipmentStatus status = ShipmentStatus.CREATED;

    public Shipment(String reference, Customer customer, Planet origin, Planet destination, Ship ship, LocalDate departureDate) {
        this.reference = reference;
        this.customer = customer;
        this.origin = origin;
        this.destination = destination;
        this.ship = ship;
        this.departureDate = departureDate;
    }

    public double getTotalWeight() {
        double totalWeight = 0;
        for (Cargo item : getCargo()) {
            totalWeight += item.getWeight();
        }
        return totalWeight;
    }

    public boolean hasHazardousCargo() {
        for (Cargo item : getCargo()) {
            if (item.isHazardous()) { return true; }
        }
        return false;
    }

    public double getTotalValue() {
        double totalValue = 0;
        for (Cargo item : getCargo()) {
            totalValue += item.getDeclaredValue();
        }
        return totalValue;
    }

    public void validate() throws Exception {
        validateCustomer();
        validateCargo();
    }

    private void validateCustomer() throws CustomerException {
        if (!getCustomer().isActive()) { throw new CustomerException("Customer is inactive"); }
        if (getCustomer().getAccount().isSuspended()) { throw new CustomerException("Customer is suspended"); }
    }

    private void validateCargo() throws CargoException {
        if (getCargo().isEmpty()) {
            throw new CargoException("Cargo is empty");
        }

        if (getTotalWeight() > getShip().getCapacity())
            throw new CargoException("Cargo over capacity");
        if (hasHazardousCargo() && !new PermissionService().canCarryHazardous(getShip()))
            throw new CargoException("Shipment cannot handle hazardous cargo");
    }

    public void prepareForLaunch() throws Exception {
        validate();
        updateTotal();
        setStatus(ShipmentStatus.READY);
        save();
    }

    private void updateTotal() {
        total = new PricingService().calculatePrice(this);
    }

    public String display() {
        String output =  getPriority() + " | " + getReference() + " | " + String.format("%.2f", total) + " | ";
        output += "CONFIRMATION " + getReference() + " -> " + getCustomer().getName();
        return output;
    }

    private ShipmentPriority getPriority() {
        return total > PRIORITY_AMOUNT ? ShipmentPriority.PRIORITY : ShipmentPriority.REGULAR;
    }

    private void save() {
        new ManifestRepository().save(this);
    }

    public void addCargo(Cargo item) { cargo.add(item); }
    public String getReference() { return reference; }
    public Customer getCustomer() { return customer; }
    public Planet getOrigin() { return origin; }
    public Planet getDestination() { return destination; }
    public Ship getShip() { return ship; }
    public LocalDate getDepartureDate() { return departureDate; }
    public List<Cargo> getCargo() { return cargo; }
    public double getTotal() { return total; }
    public ShipmentStatus getStatus() { return status; }
    public void setStatus(ShipmentStatus status) { this.status = status; }
}
