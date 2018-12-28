package cn.swift.chapter7;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

import cn.swift.annotation.Sucks;

/**
 * 7-3 不可靠的取消操作把生产者置于阻塞的操作中
 */
@Sucks
public class BrokenPrimerProducer extends Thread {

	private final BlockingQueue<BigInteger> queue;

	private volatile boolean cancelled;

	public BrokenPrimerProducer(BlockingQueue<BigInteger> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			BigInteger p = BigInteger.ONE;
			while (!cancelled) {
				queue.put(p.nextProbablePrime());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void cancel() {
		this.cancelled = true;
	}
	
}
