package cn.swift.chapter4;

import java.util.concurrent.atomic.AtomicInteger;

import cn.swift.annotation.NotThreadSafe;

/**
 * 4-10 NumberRange不足以保护它的不变性条件
 */
@NotThreadSafe
public class NumberRange {

    /**
     * 不变性条件：lower <= upper
     */
    private final AtomicInteger lower = new AtomicInteger();
    private final AtomicInteger upper = new AtomicInteger();

    public void setLower(int i) {
	if (i > upper.get()) {
	    // ！不安全的先检查后执行
	    throw new IllegalArgumentException("can't set lower to " + i + " > upper.");
	}
	lower.set(i);
    }

    public void setUpper(int i) {
	if (i < lower.get()) {
	    // ！不安全的先检查后执行
	    throw new IllegalArgumentException("can't set upper to " + i + " < lower.");
	}
	upper.set(i);
    }

    public boolean isInRange(int i) {
	return (i < upper.get() && i > lower.get());
    }
}
