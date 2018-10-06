package cn.swift.chapter3;

import cn.swift.annotation.GuardedBy;
import cn.swift.annotation.ThreadSafe;

/**
 * 3-3 线程安全的可变整数类
 * 
 * @author Swift Hu
 * @date 2018年9月12日 下午1:34:10
 */
@ThreadSafe
public class SynchronizedInteger {

	@GuardedBy("this")
	private int value;

	public synchronized int get() {
		return value;
	}

	public synchronized void set(int value) {
		this.value = value;
	}
}
