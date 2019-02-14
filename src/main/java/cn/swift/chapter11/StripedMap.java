package cn.swift.chapter11;

import cn.swift.annotation.ThreadSafe;

/**
 * 11-8 在基于散列的Map中使用锁分段技术
 */
@ThreadSafe
public class StripedMap {

    // 同步策略：bucket[n]由locks[n%N_LOCKS]来保护
    private static final int N_LOCKS = 16;

    private final Object[] locks;

    private final Node[] buckets;

    public StripedMap(int numBuckets) {
        buckets = new Node[numBuckets];
        locks = new Object[N_LOCKS];
        for (int i = 0; i < N_LOCKS; i++) {
            locks[i] = new Object();
        }
    }

    private final int hash(Object key) {
        return Math.abs(key.hashCode() % buckets.length);
    }

    public Object get(Object key) {
        int hash = hash(key);
        synchronized (locks[hash % N_LOCKS]) {
            for (Node m = buckets[hash]; m != null; m = m.next) {
                if (m.key.equals(key)) {
                    return m.value;
                }
            }
        }
        return null;
    }

    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            synchronized (locks[i % N_LOCKS]) {
                buckets[i] = null;
            }
        }
    }

    private static class Node {

        Object key;

        Object value;

        Node next;
    }
}
