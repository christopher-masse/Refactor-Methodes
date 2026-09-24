package test.java.service;
import main.java.domain.Customer;
import main.java.domain.CustomerAccount;
import main.java.exception.InsufficientFundsException;
import main.java.service.AccountService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest {
    AccountService accountService;

    public AccountServiceTest() {
        accountService = new AccountService();
    }

    @Test
    public void testWithdrawInvalid() {
        Customer customer = makeCustomer(8);
        assertThrows(InsufficientFundsException.class, () -> {
            accountService.withdraw(customer, 10000);
        });
    }

    @Test
    public void testWithdrawOk() {
        Customer customer = makeCustomer(8);
        assertDoesNotThrow(() -> accountService.withdraw(customer, 1000));
    }

    @Test
    public void testRewardPointsHigh() {
        Customer customer = makeCustomer(8);
        assertEquals(500, accountService.getRewardPoints(customer));
    }


    @Test
    public void testRewardPointsLow() {
        Customer customer = makeCustomer(2);
        assertEquals(100, accountService.getRewardPoints(customer));
    }

    @Test
    public void testRewardPointsNegative() {
        Customer customer = makeCustomer(-2);
        assertEquals(100, accountService.getRewardPoints(customer));
    }

    private Customer makeCustomer(int loyaltyYear) {
        return new Customer(1, "Test Customer", loyaltyYear, true,
                new CustomerAccount(true, 5000));
    }
}
