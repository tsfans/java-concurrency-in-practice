package cn.swift.chapter8.puzzle;

import java.util.concurrent.CountDownLatch;

import cn.swift.annotation.GuardedBy;
import cn.swift.annotation.ThreadSafe;

/**
 * 8-17 携带结果的闭锁
 */
@ThreadSafe
public class ValueLatch<T> {

    @GuardedBy("this")
    private T value = null;

    private final CountDownLatch done = new CountDownLatch(1);

    public boolean isSet() {
        return done.getCount() == 0;
    }

    public synchronized void setValue(T value) {
        if (!isSet()) {
            this.value = value;
            done.countDown();
        }
    }

    public synchronized T getValue() throws InterruptedException {
        done.await();
        synchronized (this) {
            return value;
        }
    }
}
