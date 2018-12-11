package cn.swift.chapter5;

import java.util.Vector;

import cn.swift.annotation.NotThreadSafe;

/**
 * 5-1 Vector上可能导致混乱结果的复合操作
 */
@NotThreadSafe
public class MessUpVector {

    public static Object getLast(Vector<Object> list) {
	int lastIndex = list.size() - 1;
	return list.get(lastIndex);
    }

    public static void deleteLast(Vector<Object> list) {
	int lastIndex = list.size() - 1;
	list.remove(lastIndex);
    }
}
