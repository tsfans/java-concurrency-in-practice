package cn.swift.chapter5.cache;

import java.util.HashMap;
import java.util.Map;

import cn.swift.annotation.GuardedBy;

/**
 * 5-16 使用HashMap和同步机制来初始化缓存
 * 线程安全但并发性非常糟糕，性能甚至可能低于不用缓存的情况
 */
public class Memorizer1<A, V> extends AbstractMemorizer<A, V> {

    @GuardedBy("this")
    private final Map<A, V> cache = new HashMap<>();

    private final Computable<A, V> c;

    public Memorizer1(Computable<A, V> c) {
	this.c = c;
    }

    @Override
    public synchronized V compute(A arg) throws InterruptedException {
	V result = cache.get(arg);
	if (result == null) {
	    result = c.compute(arg);
	    cache.put(arg, result);
	}
	return result;
    }

}
