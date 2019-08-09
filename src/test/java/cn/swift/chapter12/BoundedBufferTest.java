package cn.swift.chapter12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;

public class BoundedBufferTest {

    private static final int CAPACITY = 10;

    private static final long LOCKUP_DETECT_TIMEOUT = 1000;
    private static final int CAPACITY2 = 10000;
    private static final int THRESHOLD = 10000;

    private final TestingThreadFactory threadFactory = new TestingThreadFactory();

    @Test
    void testIsEmptyWhenConstructed() {
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(CAPACITY);
        assertTrue(bb.isEmpty());
        assertFalse(bb.isFull());
    }

    @Test
    void testIsFullAfterPuts() throws InterruptedException {
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(CAPACITY);
        for (int i = 0; i < CAPACITY; i++) {
            bb.put(i);
        }
        assertTrue(bb.isFull());
        assertFalse(bb.isEmpty());

    }

    @Test
    void testTakeBlocksWhenEmpty() {
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(CAPACITY);
        Thread taker = new Thread(() -> {
            try {
                int i = bb.take();
                // 如果执行到这里，那么表示出现了一个错误
                fail();
            } catch (InterruptedException success) {
            }
        });
        try {
            taker.start();
            Thread.sleep(LOCKUP_DETECT_TIMEOUT);
            taker.interrupt();
            taker.join(LOCKUP_DETECT_TIMEOUT);
            assertFalse(taker.isAlive());
        } catch (Exception unexpected) {
            fail();
        }
    }

    @Test
    void testPutTake() {
        boolean result = new PutTakeTest(10, 10, 1000).test();
        assertTrue(result);
    }

    @Test
    void testPoolExpansion() throws InterruptedException {
        int maxSize = 10;
        ExecutorService exec = Executors.newFixedThreadPool(maxSize, threadFactory);
        for (int i = 0; i < 10 * maxSize; i++) {
            exec.execute(() -> {
                try {
                    Thread.sleep(Long.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            });
        }
        for (int i = 0; i < 20 && threadFactory.numCreated.get() < maxSize; i++) {
            Thread.sleep(100);
        }
        assertEquals(threadFactory.numCreated.get(), maxSize);
        exec.shutdownNow();
    }

    @Test
    void testTimedPutTake() {
        boolean result = new TimedPutTakeTest(10, 10, 1000).test();
        assertTrue(result);
    }

}
