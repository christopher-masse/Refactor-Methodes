package main.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Customer {
    private final int id;
    private String name;
    private final LocalDate createdDate;
    private boolean active;
    private CustomerAccount account;


    public Customer(int id, String name, LocalDate createdDate, boolean active, CustomerAccount account) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.active = active;
        this.account = account;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getLoyaltyYears() {
        return (int) ChronoUnit.YEARS.between(LocalDate.now(), createdDate);
    }
    public boolean isActive() { return active; }
    public CustomerAccount getAccount() { return account; }
}
