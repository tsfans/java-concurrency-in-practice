package cn.swift.exception;

import java.math.BigDecimal;
import java.util.Random;

/**
 * 10-4 在典型条件下会发生死锁的循环
 */
public class DemonstrateDeadlock {

    private static final int NUM_THREADS = 20;

    private static final int NUM_ACCOUNTS = 5;

    private static final int NUM_ITERATIONS = 1000000;

    public static void main(String[] args) {
        final Random random = new Random();
        final Account[] accounts = new Account[NUM_ACCOUNTS];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account();
        }

        class TransferThread extends Thread {
            @Override
            public void run() {
                for (int i = 0; i < NUM_ITERATIONS; i++) {
                    int fromAcc = random.nextInt(NUM_ACCOUNTS);
                    int toAcc = random.nextInt(NUM_ACCOUNTS);
                    BigDecimal amount = new BigDecimal(random.nextInt(1000));
                    transferMoney(accounts[fromAcc], accounts[toAcc], amount);
                }
            }

            private void transferMoney(Account account, Account account2, BigDecimal amount) {
            }
        }

        for (int i = 0; i < NUM_THREADS; i++) {
            new TransferThread().start();
        }
    }

}

class Account {
}
