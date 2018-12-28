package cn.swift.chapter7;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 7-2 一个仅运行一秒的素数生成器
 */
public class PrimeGenertorTest {

	public List<BigInteger> aSecondOfPrime() throws InterruptedException {
		PrimeGenerator genertor = new PrimeGenerator();
		new Thread(genertor).start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} finally {
			genertor.cancel();
		}
		return genertor.get();
	}
}
