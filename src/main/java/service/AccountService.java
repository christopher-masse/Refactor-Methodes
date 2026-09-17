package main.java.service;

import main.java.domain.Customer;
import main.java.exception.InsufficientFundsException;

public class AccountService {
    final int REWARD_POINTS_HIGH = 500;
    final int REWARD_POINTS_LOW = 100;
    final int LOYALTY_YEARS = 5;

    public void withdraw(Customer customer, double amount) throws InsufficientFundsException {
        if (customer.getAccount().getBalance() < amount) throw new InsufficientFundsException("Insufficient funds");
        customer.getAccount().setBalance(customer.getAccount().getBalance() - amount);
    }

    public int getRewardPoints(Customer customer) {
        return customer.getLoyaltyYears() > LOYALTY_YEARS ? REWARD_POINTS_HIGH : REWARD_POINTS_LOW;
    }
}
