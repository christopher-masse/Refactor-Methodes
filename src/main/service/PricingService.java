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
    private final double BASE_INSURANCE_RATE = 0.02;
    private final int INSURANCE_HAZARDOUS_RATE = 75;
    private final int LOYALTY_INSURANCE_DISCOUNT = 10;
    private final int LOYALTY_INSURANCE_DISCOUNT_RATE = 10;

    public double calculatePrice(Shipment shipment) {
        double price = 0;

        price += getWeightPrice(shipment.getTotalWeight());
        price += getHighValueRate(shipment.getTotalValue());
        price += getHazardousRate(price, shipment.hasHazardousCargo());
        price += getSecurityRate(shipment.getOrigin(), shipment.getDestination());
        price += getSectorRate(shipment.getOrigin(), shipment.getDestination());
        price += getHolidaysRate(shipment.getDepartureDate());
        price += getLoyaltyDiscountRate(price, shipment.getCustomer());
        price += getInsuranceRate(shipment);

        return price;
    }

    private double getWeightPrice(double weight) {
        return weight * PRICE_PER_POUND;
    }

    private double getHighValueRate(double value) {
        return value > HIGH_VALUE_LIMIT ? value * HIGH_VALUE_RATE : 0;
    }

    private double getHazardousRate(double price, boolean hasHazardous) {
        return hasHazardous ? price * HAZARDOUS_RATE : 0;
    }

    private double getSecurityRate(Planet origin, Planet destination) {
        return origin.getSecurityLevel().isHigherThan(SecurityLevel.HIGH)
                || destination.getSecurityLevel().isHigherThan(SecurityLevel.HIGH) ? SECURITY_RATE : 0;
    }

    private double getSectorRate(Planet origin, Planet destination) {
        return !origin.getSector().equals(destination.getSector()) ? SECTOR_RATE : 0;
    }

    private double getHolidaysRate(LocalDate departureDate) {
        return departureDate.getMonthValue() == 12 || departureDate.getMonthValue() <= 2 ? HOLIDAYS_RATE : 0;
    }

    private double getLoyaltyDiscountRate(double price, Customer customer) {
        return customer.getLoyaltyYears() >= LOYALTY_DISCOUNT ? price * LOYALTY_DISCOUNT_RATE - price : 0;
    }

    private double getInsuranceRate(Shipment shipment) {
        double price = shipment.getTotalValue() * BASE_INSURANCE_RATE;
        if (shipment.hasHazardousCargo()) price += INSURANCE_HAZARDOUS_RATE;
        if (shipment.getCustomer().getLoyaltyYears() >= LOYALTY_INSURANCE_DISCOUNT) price -= LOYALTY_INSURANCE_DISCOUNT_RATE;
        return Math.max(price, 0);
    }

}
