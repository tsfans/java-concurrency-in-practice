package cn.swift.chapter5;

import java.util.Vector;

import cn.swift.annotation.ThreadSafe;

/**
 * 5-2 在客户端加锁的Vector上的复合操作
 */
@ThreadSafe
public class SafeVector {

    public static Object getLast(Vector<Object> list) {
	synchronized(list) {
	    int lastIndex = list.size() - 1;
	    return list.get(lastIndex);
	}
    }
    
    public static void deleteLast(Vector<Object> list) {
	synchronized(list) {
	    int lastIndex = list.size() - 1;
	    list.remove(lastIndex);
	}
    }
}
