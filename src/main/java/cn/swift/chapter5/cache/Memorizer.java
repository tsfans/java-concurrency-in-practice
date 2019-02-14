package cn.swift.chapter5.cache;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 5-19 Memorizer的最终实现
 * 当cache中存的是future时，将有可能导致缓存污染（Cache Pollution）
 * 此实现没有解决缓存逾期问题，无法及时清除无效缓存
 */
public class Memorizer<A, V> extends AbstractMemorizer<A, V> {

    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> c;

    public Memorizer(Computable<A, V> c) {
	this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
	Future<V> f = cache.get(arg);
	if (f == null) {
	    Callable<V> eval = () -> c.compute(arg);
	    FutureTask<V> ft = new FutureTask<>(eval);
	    f = cache.putIfAbsent(arg, ft);
	    if (f == null) {
		ft.run();
	    }
	}
	try {
	    return f.get();
	} catch (ExecutionException e) {
	    throw launderThrowable(e.getCause());
	}
    }

}
