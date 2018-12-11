package cn.swift.chapter5.cache;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 5-18 基于FutureTask和ConcurrentHashMap的Memorizer封装器
 * 但其仍然存在重复计算的可能，因为if代码块是非原子的
 * 为了避免复合操作的漏洞，可以使用ConcurrentHashMap的putIfAbsent方法
 */
public class Memorizer3<A, V> extends AbstractMemorizer<A, V> {

    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> c;

    public Memorizer3(Computable<A, V> c) {
	this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
	Future<V> f = cache.get(arg);
	if (f == null) {
	    Callable<V> eval = () -> {return c.compute(arg);};
	    FutureTask<V> ft = new FutureTask<V>(eval);
	    f = ft;
	    cache.put(arg, f);
	    // 调用c.compute(arg)
	    ft.run();
	}
	try {
	    return f.get();
	} catch (ExecutionException e) {
	    throw launderThrowable(e.getCause());
	}
    }
}
