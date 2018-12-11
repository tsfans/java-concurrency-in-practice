package cn.swift.chapter5;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import cn.swift.annotation.NotThreadSafe;

/**
 * 5-6 隐藏在字符串连接中的迭代操作
 */
@NotThreadSafe
public class HiddenIterator {

    private final Set<Integer> set = new HashSet<>();

    public synchronized void add(Integer i) {
	set.add(i);
    }

    public synchronized void remove(Integer i) {
	set.remove(i);
    }

    public void addTenThings() {
	Random r = new Random();
	for (int i = 0; i < 10; i++) {
	    add(r.nextInt());
	}
	/**
	 * 编译器将字符串的连接操作转换为调用StringBuilder.append(Object)
	 * 而这个方法又会调用容器的toString()方法，标准容器的toString()将迭代容器
	 * 并在每个元素上调用toString()
	 */
	System.out.println("DEBUG: add ten elemnets to set" + set);
    }
}
