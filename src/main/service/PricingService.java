package main.service;

import main.domain.Customer;
import main.domain.Planet;
import main.enums.Sector;
import main.enums.SecurityLevel;

import java.time.LocalDate;

public class PricingService {
    public double increaseByPercent(double price, double percent) { return price + price * percent; }

    public double calculatePrice(double weight, double declaredValue, boolean hazardous,
                                 String originName, Sector originSector, SecurityLevel originSecurity,
                                 String destinationName, Sector destinationSector, SecurityLevel destinationSecurity,
                                 int loyaltyYears, boolean active, boolean suspended,
                                 LocalDate departureDate) {
        double result = weight * 2.25;
        if (declaredValue > 10000) result += declaredValue * 0.015;
        if (hazardous) result = increaseByPercent(result, 0.2);
        if (originSecurity.isHigherThan(SecurityLevel.HIGH) || destinationSecurity.isHigherThan(SecurityLevel.HIGH)) result += 125;
        if (!originSector.equals(destinationSector)) result += 80;
        if (departureDate.getMonthValue() == 12 || departureDate.getMonthValue() <= 2) result += 45;
        if (loyaltyYears >= 5 && active && !suspended) result *= 0.90;
        return result;
    }

    public double calculateInsurance(double value, boolean hazardous, Customer customer) {
        value = value * 0.02;
        if (hazardous) value += 75;
        if (customer.getLoyaltyYears() >= 10) value -= 10;
        return Math.max(value, 0);
    }

    public String priceCategory(double price) {
        double temporaryPrice = price;
        return temporaryPrice > 1000 ? "HIGH" : "STANDARD";
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
