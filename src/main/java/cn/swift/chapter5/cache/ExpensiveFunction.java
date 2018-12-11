package cn.swift.chapter5.cache;

import java.math.BigInteger;

public class ExpensiveFunction implements Computable<String, BigInteger>{

    @Override
    public BigInteger compute(String arg) throws InterruptedException {
	// compute that take a long time
	return new BigInteger(arg);
    }

}
