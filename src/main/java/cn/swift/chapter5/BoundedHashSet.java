package cn.swift.chapter5;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 5-14 使用Semaphore为容器设置边界
 */
public class BoundedHashSet<T> {

    private final Set<T> set;

    private final Semaphore sem;

    public BoundedHashSet(int bound) {
	this.set = Collections.synchronizedSet(new HashSet<T>());
	sem = new Semaphore(bound);
    }

    public boolean add(T o) throws InterruptedException {
	sem.acquire();
	boolean wasAdded = set.add(o);
	if (!wasAdded) {
	    sem.release();
	}
	return wasAdded;
    }

    public boolean remove(Object o) {
	boolean wasRemoved = set.remove(o);
	if (wasRemoved) {
	    sem.release();
	}
	return wasRemoved;
    }
}
