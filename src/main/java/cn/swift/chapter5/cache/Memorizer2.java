package cn.swift.chapter5.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 5-17 使用ConcurrentHashMap替换HashMap
 * 能获得更好的并发性能，但也有漏洞：可能会导致重复计算
 * 我们的期望是如果有一个线程x已经开始了某个耗时的计算
 * 当别的线程查找同样的结果时，它能知道等待线程x是最高效的方法
 */
public class Memorizer2<A, V> extends AbstractMemorizer<A, V> {

    private final Map<A, V> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> c;

    public Memorizer2(Computable<A, V> c) {
	this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
	V result = cache.get(arg);
	if (result == null) {
	    result = c.compute(arg);
	    cache.put(arg, result);
	}
	return result;
    }

}
