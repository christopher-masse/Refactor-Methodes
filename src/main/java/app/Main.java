package main.java.app;

import main.java.domain.*;
import main.java.enums.Sector;
import main.java.enums.SecurityLevel;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Shipment shipment = createTestShipment();

        try {
            shipment.prepareForLaunch();
            System.out.println(shipment.display());
        } catch (Exception ex) {
            System.out.println("Invalid shipment: " + ex.getMessage());
        }


    }

    private static Shipment createTestShipment() {
        Customer customer = new Customer(42, "Nova Trading", 8, true, new CustomerAccount(false, 5000));

        Planet origin = new Planet("Corellia", Sector.CORE, SecurityLevel.HIGH);
        Planet destination = new Planet("Nar Shaddaa", Sector.HUTT_SPACE, SecurityLevel.MAXIMUM);

        Ship ship = new Ship("YT-2400-77", "Light Freighter", 2500,
                Permissions.VIEW_MANIFEST | Permissions.EDIT_MANIFEST);

        Shipment shipment = new Shipment("GF-2026-001", customer, origin, destination, ship,
                LocalDate.of(2026, 12, 12));

        shipment.addCargo(new Cargo("Medical supplies", 500, 18000, false));
        shipment.addCargo(new Cargo("Volatile fuel cells", 100, 9000, true));

        return shipment;
    }
}
