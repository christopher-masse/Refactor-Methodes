package main.domain;

public class CustomerAccount {
    private boolean suspended;
    private double accountBalance;

    public CustomerAccount(boolean suspended, double accountBalance) {
        this.suspended = suspended;
        this.accountBalance = accountBalance;
    }

    public boolean isSuspended() { return suspended; }
    public double getAccountBalance() { return accountBalance; }
    public void setAccountBalance(double accountBalance) { this.accountBalance = accountBalance; }
}
