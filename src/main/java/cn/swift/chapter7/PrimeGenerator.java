package cn.swift.chapter7;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import cn.swift.annotation.GuardedBy;

/**
 * 7-1 使用volatile类型的域来保存取消状态
 */
public class PrimeGenerator implements Runnable {

	@GuardedBy("this")
	private final List<BigInteger> primes = new ArrayList<>();

	private volatile boolean cancelled;

	@Override
	public void run() {
		BigInteger p = BigInteger.ONE;
		while (!cancelled) {
			p = p.nextProbablePrime();
			synchronized (this) {
				primes.add(p);
			}
		}
	}

	public void cancel() {
		cancelled = true;
	}

	public synchronized List<BigInteger> get() {
		return new ArrayList<>(primes);
	}

}
