package main.service;

import main.domain.Customer;
import main.domain.Planet;
import main.domain.Shipment;
import main.enums.Sector;
import main.enums.SecurityLevel;

import java.time.LocalDate;

public class PricingService {
    private final double PRICE_PER_POUND = 2.25;
    private final int SECURITY_RATE = 125;
    private final double HIGH_VALUE_LIMIT = 10000;
    private final double HIGH_VALUE_RATE = 0.015;
    private final int SECTOR_RATE = 80;
    private final int HOLIDAYS_RATE = 45;
    private final int LOYALTY_DISCOUNT = 5;
    private final double LOYALTY_DISCOUNT_RATE = 0.9;
    private final double HAZARDOUS_RATE = 0.2;

    public double increaseByPercent(double price, double percent) { return price + price * percent; }

    public double calculatePrice(Shipment shipment) {
        double result = shipment.getTotalWeight() * PRICE_PER_POUND;
        if (shipment.getTotalValue() > HIGH_VALUE_LIMIT) result += shipment.getTotalValue() * HIGH_VALUE_RATE;
        if (shipment.hasHazardousCargo()) result = increaseByPercent(result, HAZARDOUS_RATE);
        if (shipment.getOrigin().getSecurityLevel().isHigherThan(SecurityLevel.HIGH)
                || shipment.getDestination().getSecurityLevel().isHigherThan(SecurityLevel.HIGH)) result += SECURITY_RATE;
        if (!shipment.getOrigin().getSector().equals(shipment.getDestination().getSector())) result += SECTOR_RATE;
        if (shipment.getDepartureDate().getMonthValue() == 12 || shipment.getDepartureDate().getMonthValue() <= 2) result += HOLIDAYS_RATE;
        if (shipment.getCustomer().getLoyaltyYears() >= LOYALTY_DISCOUNT) result *= LOYALTY_DISCOUNT_RATE;
        return result;
    }

    public double calculateInsurance(double value, boolean hazardous, Customer customer) {
        value = value * 0.02;
        if (hazardous) value += 75;
        if (customer.getLoyaltyYears() >= 10) value -= 10;
        return Math.max(value, 0);
    }

    public String priceCategory(double price) {
        return price > 1000 ? "HIGH" : "STANDARD";
    }

    public double calculateRouteSurcharge(Planet origin, Planet destination) {
        int temporary = origin.getSecurityLevel().getLevel() + destination.getSecurityLevel().getLevel();
        double surcharge = temporary * 12.5;
        temporary = origin.getSector().equals(destination.getSector()) ? 0 : 1;
        surcharge += temporary * 80;
        return surcharge;
    }

    public Object pricingSummary(double total) {
        return total >= 2000 ? "PRIORITY" : "REGULAR";
    }
}
