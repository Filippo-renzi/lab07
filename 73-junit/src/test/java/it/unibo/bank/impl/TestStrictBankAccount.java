package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    private static final int ACCEPTABLE_MESSAGE_LENGTH = 10;
    private static final int ONE_ONDRED_DOLLAR = 100;
    private static final int NEGATIVE_AMMOUNT = -100;
    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 0);
        this.bankAccount = new StrictBankAccount(mRossi, 0.0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    void testInitialization() {
        assertEquals(mRossi, this.bankAccount.getAccountHolder());
        assertEquals(0.0, this.bankAccount.getBalance());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    void testManagementFees() {
        bankAccount.deposit(mRossi.getUserID(), ONE_ONDRED_DOLLAR);
        bankAccount.chargeManagementFees(mRossi.getUserID());
        final double afterDeposit = bankAccount.getBalance() 
            - (SimpleBankAccount.MANAGEMENT_FEE 
            + bankAccount.getTransactionsCount() 
            * StrictBankAccount.TRANSACTION_FEE);
        assertEquals(afterDeposit, bankAccount.getBalance());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    void testNegativeWithdraw() {
        final double preBalance = bankAccount.getBalance();
        try {
            bankAccount.withdraw(mRossi.getUserID(), NEGATIVE_AMMOUNT);
            Assertions.fail("The negative deposit was possible, but it shouldn'd");
        } catch (final IllegalArgumentException e) {
            assertEquals(preBalance, bankAccount.getBalance());
            assertNotNull(e.getMessage());  //Not null message
            assertFalse(e.getMessage().isBlank()); // Not a blank or empty message
            assertTrue(e.getMessage().length() >= ACCEPTABLE_MESSAGE_LENGTH); // A message with a decent length
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    void testWithdrawingTooMuch() {
        try {
            bankAccount.withdraw(mRossi.getUserID(), ONE_ONDRED_DOLLAR);
            Assertions.fail("The negative withdraw was possible, but it shouldn'd");
        } catch (final IllegalArgumentException e) {
            assertNotNull(e.getMessage());  //Not null message
            assertFalse(e.getMessage().isBlank()); // Not a blank or empty message
            assertTrue(e.getMessage().length() >= ACCEPTABLE_MESSAGE_LENGTH); // A message with a decent length
        }
    }
}
