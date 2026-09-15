package main.service;

import main.domain.Customer;
import main.exception.InsufficientFundsException;

public class AccountService {
    private final int LOYALTY_YEARS_REQUIRED = 5;
    private final int REWARD_LOYALTY = 500;
    private final int REWARD_REGULAR = 100;
    
    public void withdraw(Customer customer, double amount) throws InsufficientFundsException {
        if (customer.getAccount().getAccountBalance() < amount) {
            throw new InsufficientFundsException("Fonds insuffisant");
        }
        customer.getAccount().setAccountBalance(customer.getAccount().getAccountBalance() - amount);
    }

    public int getRewardPoints(Customer customer) {
        return customer.getLoyaltyYears() > LOYALTY_YEARS_REQUIRED
                ? REWARD_LOYALTY
                : REWARD_REGULAR;
    }
}
