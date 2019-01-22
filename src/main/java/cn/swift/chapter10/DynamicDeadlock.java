package cn.swift.chapter10;

import java.math.BigDecimal;

import cn.swift.annotation.Sucks;
import cn.swift.exception.InsufficientFundsException;

public class DynamicDeadlock {

    /**
     * 10-2 动态的锁顺序死锁
     */
    @Sucks
    public void transferMoney(Account fromAccount, Account toAccount, BigDecimal amount)
            throws InsufficientFundsException {
        synchronized (fromAccount) {
            synchronized (toAccount) {
                if (fromAccount.getBalance().compareTo(amount) < 0) {
                    throw new InsufficientFundsException("Don't have enough money!");
                } else {
                    fromAccount.debit(amount);
                    toAccount.credit(amount);
                }
            }
        }
    }

    private static final Object tieLock = new Object();

    /**
     * 10-3 通过锁顺序避免死锁
     */
    public void transferMoney2(Account fromAccount, Account toAccount, BigDecimal amount)
            throws InsufficientFundsException {
        class Helper {

            public void transfer() throws InsufficientFundsException {
                if (fromAccount.getBalance().compareTo(amount) < 0) {
                    throw new InsufficientFundsException("Don't have enough money!");
                } else {
                    fromAccount.debit(amount);
                    toAccount.credit(amount);
                }
            }
        }

        int fromHash = System.identityHashCode(fromAccount);
        int toHash = System.identityHashCode(toAccount);

        if (fromHash < toHash) {
            synchronized (fromAccount) {
                synchronized (toAccount) {
                    new Helper().transfer();
                }
            }
        } else if (fromHash > toHash) {
            synchronized (toAccount) {
                synchronized (fromAccount) {
                    new Helper().transfer();
                }
            }
        } else {
            synchronized (tieLock) {
                synchronized (fromAccount) {
                    synchronized (toAccount) {
                        new Helper().transfer();
                    }
                }
            }
        }
    }

    class Account {

        public void credit(BigDecimal amount) {

        }

        public void debit(BigDecimal amount) {

        }

        public BigDecimal getBalance() {
            return new BigDecimal(0);
        }
    }
}
