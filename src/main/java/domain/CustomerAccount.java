package main.java.domain;

public class CustomerAccount {
    private boolean suspended;
    private double balance;

    public CustomerAccount(boolean suspended, double balance) {
        this.suspended = suspended;
        this.balance = balance;
    }

    public boolean isSuspended() { return suspended; }
    public double getBalance() { return balance; }
    public void setBalance(double accountBalance) { this.balance = accountBalance; }
}
