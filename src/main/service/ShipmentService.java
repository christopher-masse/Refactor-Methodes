package main.service;

import main.domain.Cargo;
import main.domain.Shipment;
import main.exception.CargoException;
import main.exception.CustomerException;

public class ShipmentService {

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

    public double getTotalValue(Shipment shipment) {
        double totalValue = 0;
        for (Cargo item : shipment.getCargo()) {
            totalValue += item.getDeclaredValue();
        }
        return totalValue;
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
        if (hasHazardousCargo(shipment) && !new PermissionService().canCarryHazardous(shipment.getShip())) throw new CargoException("Shipment cannot handle hazardous cargo");
    }

    public String calculatePrintSaveAndNotify(Shipment shipment) {

        double total = new PricingService().calculatePrice(shipment);
        total += new PricingService().calculateInsurance(getTotalValue(shipment), hasHazardousCargo(shipment), shipment.getCustomer());

        shipment.setTotal(total);
        shipment.setStatus("READY");

        String output = (total > 2000)
                ? "PRIORITY | " + shipment.getReference() + " | " + String.format("%.2f", total) + " | " + new NotificationService().confirmationFor(shipment)
                : "REGULAR | " + shipment.getReference() + " | " + String.format("%.2f", total) + " | " + new NotificationService().confirmationFor(shipment);

        new ManifestRepository().save(shipment);
        return output;

    }
}
