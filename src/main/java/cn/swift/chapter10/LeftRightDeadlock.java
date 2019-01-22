package cn.swift.chapter10;

import cn.swift.annotation.Sucks;

/**
 * 10-1 简单的锁顺序死锁
 */
@Sucks
public class LeftRightDeadlock {

    private final Object left = new Object();

    private final Object right = new Object();

    public void leftRight() {
        synchronized (left) {
            synchronized (right) {
                doSomething();
            }
        }
    }

    public void rightLeft() {
        synchronized (right) {
            synchronized (left) {
                doSomethingElse();
            }
        }
    }

    private void doSomethingElse() {

    }

    private void doSomething() {

    }
}
