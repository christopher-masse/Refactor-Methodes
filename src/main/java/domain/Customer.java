package main.java.domain;

public class Customer {
    private final int id;
    private String name;
    private int loyaltyYears;
    private boolean active;
    private CustomerAccount account;

    public Customer(int id, String name, int loyaltyYears, boolean active, CustomerAccount account) {
        this.id = id;
        this.name = name;
        this.loyaltyYears = loyaltyYears;
        this.active = active;
        this.account = account;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getLoyaltyYears() { return loyaltyYears; }
    public boolean isActive() { return active; }
    public CustomerAccount getAccount() { return account; }
}
