package main.service;

import main.domain.Cargo;
import main.domain.Shipment;
import main.enums.SecurityLevel;

public class RiskService {
    public String evaluate(Shipment shipment) {
        if ((shipment.getDestination().getSecurityLevel().isHigherThan(SecurityLevel.HIGH)
                && !shipment.getOrigin().getSector().equals(shipment.getDestination().getSector()))
                || (shipment.getCargo().stream().anyMatch(Cargo::isHazardous)
                && shipment.getCargo().stream().mapToDouble(Cargo::getDeclaredValue).sum() > 50000)) {
            return "CRITICAL";
        }
        return "NORMAL";
    }
}
