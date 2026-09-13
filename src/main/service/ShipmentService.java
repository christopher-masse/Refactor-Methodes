package main.service;

import main.domain.Cargo;
import main.domain.Shipment;
import main.exception.CargoException;
import main.exception.CustomerException;

public class ShipmentService {
    private final PricingService pricingService;
    private final PermissionService permissionService;
    private final ManifestRepository repository;
    private final NotificationService notificationService;

    public ShipmentService(PricingService pricingService, PermissionService permissionService,
                           ManifestRepository repository, NotificationService notificationService) {
        this.pricingService = pricingService;
        this.permissionService = permissionService;
        this.repository = repository;
        this.notificationService = notificationService;
    }

    public double getTotalWeight(Shipment shipment) {
        double totalWeight = 0;

        for (Cargo item : shipment.getCargo()) {
            totalWeight += item.getWeight();
        }

        return totalWeight;
    }

    public boolean hasHazardousCargo(Shipment shipment) {
        for (Cargo item : shipment.getCargo()) {
            if (item.isHazardous()) { return true; }
        }
        return false;
    }

    public void validate(Shipment shipment) throws Exception {
        validateCustomer(shipment);
        validateCargo(shipment);
    }

    private void validateCustomer(Shipment shipment) throws CustomerException {
        if (!shipment.getCustomer().isActive()) { throw new CustomerException("Customer is inactive"); }
        if (shipment.getCustomer().getAccount().isSuspended()) { throw new CustomerException("Customer is suspended"); }
    }

    private void validateCargo(Shipment shipment) throws CargoException {
        if (shipment.getCargo().isEmpty()) { throw new CargoException("Cargo is empty"); }

        if (getTotalWeight(shipment) > shipment.getShip().getCapacity()) throw new CargoException("Cargo over capacity");
        if (hasHazardousCargo(shipment) && !permissionService.canCarryHazardous(shipment.getShip())) throw new CargoException("Shipment cannot handle hazardous cargo");
    }

    public String validateCalculatePrintSaveAndNotify(Shipment shipment) {
        try {
            validate(shipment);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        
        double totalWeight = 0;
        double totalValue = 0;
        boolean hazardous = false;
        for (Cargo item : shipment.getCargo()) {
            totalWeight += item.getWeight();
            totalValue += item.getDeclaredValue();
            if (item.isHazardous()) hazardous = true;
        }

        double total = pricingService.calculatePrice(Shipment shipment);
        total += pricingService.calculateInsurance(totalValue, hazardous, shipment.getCustomer());

        shipment.setTotal(total);
        shipment.setStatus("READY");

        String output = (total > 2000)
                ? "PRIORITY | " + shipment.getReference() + " | " + String.format("%.2f", total) + " | " + notificationService.confirmationFor(shipment)
                : "REGULAR | " + shipment.getReference() + " | " + String.format("%.2f", total) + " | " + notificationService.confirmationFor(shipment);

        repository.save(shipment);
        return output;

    }
}
