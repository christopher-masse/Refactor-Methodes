package main.service;

import main.domain.Customer;

public class AccountService {
    public int withdraw(Customer customer, double amount) {
        if (customer.getAccount().getBalance() < amount) return -1;
        customer.getAccount().setBalance(customer.getAccount().getBalance() - amount);
        return 0;
    }

    public boolean hasMoreThanFiveYears(Customer customer) {
        return customer.getLoyaltyYears() > 5;
    }

    public int getRewardPoints(Customer customer) {
        return hasMoreThanFiveYears(customer) ? 500 : 100;
    }
}
