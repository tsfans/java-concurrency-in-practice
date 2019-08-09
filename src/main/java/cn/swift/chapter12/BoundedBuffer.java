package cn.swift.chapter12;

import java.util.concurrent.Semaphore;

/**
 * 基于信号量的有界缓存
 * 
 * @author swift
 */
public class BoundedBuffer<E> {

    private final Semaphore availableItems;

    private final Semaphore availableSpaces;

    private final E[] items;

    private int putPosition = 0;

    private int takePosition = 0;

    @SuppressWarnings("unchecked")
    public BoundedBuffer(int capacity) {
        availableItems = new Semaphore(0);
        availableSpaces = new Semaphore(capacity);
        items = (E[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return availableItems.availablePermits() == 0;
    }

    public boolean isFull() {
        return availableSpaces.availablePermits() == 0;
    }

    public void put(E e) throws InterruptedException {
        availableSpaces.acquire();
        doInsert(e);
        availableItems.release();
    }

    public E take() throws InterruptedException {
        availableItems.acquire();
        E item = doExtract();
        availableSpaces.release();
        return item;
    }

    private synchronized void doInsert(E e) {
        int i = putPosition;
        items[i] = e;
        putPosition = (++i == items.length) ? 0 : i;
    }

    private synchronized E doExtract() {
        int i = takePosition;
        E item = items[i];
        items[i] = null;
        takePosition = (++i == items.length) ? 0 : i;
        return item;
    }
}
