package main.service;

import main.domain.Cargo;
import main.domain.Shipment;
import main.exception.CargoException;
import main.exception.CustomerException;

public class ShipmentService {

    public String calculatePrintSaveAndNotify(Shipment shipment) {

        double total = new PricingService().calculatePrice(shipment);
        total += new PricingService().calculateInsurance(shipment.getTotalValue(), shipment.hasHazardousCargo(), shipment.getCustomer());

        shipment.setTotal(total);
        shipment.setStatus("READY");

        String output = (total > 2000)
                ? "PRIORITY | " + shipment.getReference() + " | " + String.format("%.2f", total) + " | " + new NotificationService().confirmationFor(shipment)
                : "REGULAR | " + shipment.getReference() + " | " + String.format("%.2f", total) + " | " + new NotificationService().confirmationFor(shipment);

        new ManifestRepository().save(shipment);
        return output;

    }
}
