package cn.swift.chapter4;

import java.util.Vector;

import cn.swift.annotation.ThreadSafe;

/**
 * 4-13 扩展Vectoer，增加一个“若没有则添加”方法
 */
@ThreadSafe
public class BetterVector<E> extends Vector<E> {

    public synchronized boolean putIfAbsent(E e) {
	boolean absent = !contains(e);
	if (absent) {
	    add(e);
	}
	return absent;
    }

}
