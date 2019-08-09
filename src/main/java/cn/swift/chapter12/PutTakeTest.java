package cn.swift.chapter12;


import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import cn.swift.utils.RNGUtils;

/**
 * 12-5 测试BoundedBuffer的生产者-消费者程序
 * 
 * @author swift
 */
public class PutTakeTest {

    private static final ExecutorService pool = Executors.newCachedThreadPool();

    private final AtomicInteger putSum = new AtomicInteger(0);

    private final AtomicInteger takeSum = new AtomicInteger(0);

    private final CyclicBarrier barrier;

    private final BoundedBuffer<Integer> bb;

    private final int nTrials;

    private final int nPairs;

    public PutTakeTest(int capacity, int ntrials, int npairs) {
        this.nTrials = ntrials;
        this.nPairs = npairs;
        this.bb = new BoundedBuffer<>(capacity);
        this.barrier = new CyclicBarrier(npairs * 2 + 1);
    }

    /**
     * 12-12 采用基于栅栏的定时器进行测试
     */
    public boolean test() {
        for (int i = 0; i < nPairs; i++) {
            pool.execute(new Producer());
            pool.execute(new Consumer());
        }
        try {
            barrier.await();// 等待所有线程就绪
            barrier.await();// 等待所有线程执行完成
            clear();
            System.out.println("putSum = " + putSum.get() + ", takeSum = " + takeSum.get());
            return putSum.get() == takeSum.get();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void clear() {
        if (!pool.isShutdown()) {
            pool.shutdown();
        }
    }

    class Producer implements Runnable {

        @Override
        public void run() {
            try {
                int seed = (this.hashCode()) ^ (int)System.nanoTime();
                int sum = 0;
                barrier.await();
                for (int i = nTrials; i > 0; --i) {
                    bb.put(seed);
                    sum += seed;
                    seed = RNGUtils.xorShift(seed);
                }
                putSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
    }

    class Consumer implements Runnable {

        @Override
        public void run() {
            try {
                barrier.await();
                int sum = 0;
                for (int i = nTrials; i > 0; --i) {
                    sum += bb.take();
                }
                takeSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
    }
}
