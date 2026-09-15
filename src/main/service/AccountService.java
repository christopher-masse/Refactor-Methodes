package main.service;

import main.domain.Customer;

public class AccountService {
    public int withdraw(Customer customer, double amount) {
        if (customer.getAccount().getAccountBalance() < amount) return -1;
        customer.getAccount().setAccountBalance(customer.getAccount().getAccountBalance() - amount);
        return 0;
    }

    public boolean hasMoreThanFiveYears(Customer customer) {
        return customer.getLoyaltyYears() > 5;
    }

    public int getRewardPoints(Customer customer) {
        return hasMoreThanFiveYears(customer) ? 500 : 100;
    }
}
