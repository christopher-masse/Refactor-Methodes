package main.app;

import main.domain.*;
import main.service.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        CustomerAccount account = new CustomerAccount(false, 5000);
        Customer customer = new Customer(42, "Nova Trading", 8, true, account);
        Planet origin = new Planet("Corellia", "Core", 3);
        Planet destination = new Planet("Nar Shaddaa", "Hutt Space", 5);
        Ship ship = new Ship("YT-2400-77", "Light Freighter", 2500,
                Permissions.VIEW_MANIFEST | Permissions.EDIT_MANIFEST | Permissions.CARRY_HAZARDOUS);
        Shipment shipment = new Shipment("GF-2026-001", customer, origin, destination, ship,
                LocalDate.of(2026, 12, 12));
        shipment.addCargo(new Cargo("Medical supplies", 500, 18000, false));
        shipment.addCargo(new Cargo("Volatile fuel cells", 100, 9000, true));

        ShipmentService service = new ShipmentService(new PricingService(), new PermissionService(),
                new ManifestRepository(), new NotificationService());
        System.out.println(service.validateCalculatePrintSaveAndNotify(shipment));
    }
}
