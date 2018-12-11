package cn.swift.chapter5;

import java.util.concurrent.CountDownLatch;

/**
 * 5-11 在计时中使用CountDownLatch来启动、停止线程
 */
public class TestHarness {

    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
	final CountDownLatch startGate = new CountDownLatch(1);
	final CountDownLatch endGate = new CountDownLatch(nThreads);

	for (int i = 0; i < nThreads; i++) {
	    Thread t = new Thread(() -> {
		try {
		    startGate.await();
		    try {
			task.run();
		    } finally {
			endGate.countDown();
		    }
		} catch (InterruptedException e) {
		}
	    });
	    t.start();
	}
	long start = System.currentTimeMillis();
	startGate.countDown();
	endGate.await();
	long end = System.currentTimeMillis();
	return end - start;
    }
}
