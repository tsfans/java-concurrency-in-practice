package cn.swift.chapter3;

import java.math.BigInteger;
import java.util.Arrays;

import cn.swift.annotation.Immutable;

/**
 * 3-12 缓存数值的不可变容器类
 */
@Immutable
public class OneValueCache {

    private final BigInteger lastNumber;

    private final BigInteger[] lastFactors;

    public OneValueCache(BigInteger i, BigInteger[] factors) {
	this.lastNumber = i;
	this.lastFactors = Arrays.copyOf(factors, factors.length);
    }

    public BigInteger[] getFactors(BigInteger i) {
	if (lastNumber == null || !lastNumber.equals(i)) {
	    return null;
	} else {
	    return Arrays.copyOf(lastFactors, lastFactors.length);
	}
    }
}
