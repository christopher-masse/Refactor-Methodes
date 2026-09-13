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

        double totalWeight = 0;
        boolean hazardous = false;
        for (Cargo item : shipment.getCargo()) {
            totalWeight += item.getWeight();
            if (item.isHazardous()) hazardous = true;
        }

        if (totalWeight > shipment.getShip().getCapacity()) throw new CargoException("Cargo over capacity");
        if (hazardous && !permissionService.canCarryHazardous(shipment.getShip())) throw new CargoException("Shipment cannot handle hazardous cargo");
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

        double total = pricingService.calculatePrice(
                totalWeight, totalValue, hazardous,
                shipment.getOrigin().getName(), shipment.getOrigin().getSector(), shipment.getOrigin().getSecurityLevel(),
                shipment.getDestination().getName(), shipment.getDestination().getSector(), shipment.getDestination().getSecurityLevel(),
                shipment.getCustomer().getLoyaltyYears(), shipment.getCustomer().isActive(), shipment.getCustomer().getAccount().isSuspended(),
                shipment.getDepartureDate());
        total += pricingService.calculateInsurance(totalValue, hazardous, shipment.getCustomer());

        shipment.setTotal(total);
        shipment.setStatus("READY");
        String output;
        if (total > 2000) {
            output = "PRIORITY | " + shipment.getReference() + " | " + String.format("%.2f", total);
            repository.save(shipment);
            output += " | " + notificationService.confirmationFor(shipment);
        } else {
            output = "REGULAR | " + shipment.getReference() + " | " + String.format("%.2f", total);
            repository.save(shipment);
            output += " | " + notificationService.confirmationFor(shipment);
        }
        return output;

    }
}
