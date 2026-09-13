package main.service;

import main.domain.Cargo;
import main.domain.Shipment;
import main.enums.Risk;
import main.enums.SecurityLevel;

public class RiskService {
    public Risk evaluate(Shipment shipment) {
        boolean highSecurity = shipment.getDestination().getSecurityLevel().isHigherThan(SecurityLevel.HIGH);
        boolean sectorChange = !shipment.getOrigin().getSector().equals(shipment.getDestination().getSector());
        boolean hazardousCargo = shipment.getCargo().stream().anyMatch(Cargo::isHazardous);
        boolean highValueCargo = shipment.getCargo().stream().mapToDouble(Cargo::getDeclaredValue).sum() > 50000;

        if (highSecurity && sectorChange || hazardousCargo && highValueCargo) {
            return Risk.CRITICAL;
        }
        return Risk.NORMAL;
    }
}
