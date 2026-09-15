package main.service;

import main.domain.Customer;
import main.exception.InsufficientFundsException;

public class AccountService {
    public void withdraw(Customer customer, double amount) throws InsufficientFundsException {
        if (customer.getAccount().getAccountBalance() < amount) {
            throw new InsufficientFundsException("Fonds insuffisant");
        }
        customer.getAccount().setAccountBalance(customer.getAccount().getAccountBalance() - amount);

    public boolean hasMoreThanFiveYears(Customer customer) {
        return customer.getLoyaltyYears() > 5;
    }

    public int getRewardPoints(Customer customer) {
        return hasMoreThanFiveYears(customer) ? 500 : 100;
    }
}
