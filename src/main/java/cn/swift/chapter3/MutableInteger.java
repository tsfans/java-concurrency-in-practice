package cn.swift.chapter3;

import cn.swift.annotation.NotThreadSafe;

/**
 * 3-2 非线程安全的可变整数类
 * 
 * @author Swift Hu
 * @date 2018年9月12日 下午1:32:04
 */
@NotThreadSafe
public class MutableInteger {

	private int value;

	public int get() {
		return value;
	}

	public void set(int value) {
		this.value = value;
	}
}
