package cn.swift.chapter4;

import cn.swift.annotation.GuardedBy;
import cn.swift.annotation.ThreadSafe;
import cn.swift.chapter4.PersonSet.Person;

/**
 * 4-3 通过一个私有锁来保护状态
 */
@ThreadSafe
public class PrivateLock {

    private final Object myLock = new Object();

    @GuardedBy("myLock")
    Person person;

    void someMethod() {
	synchronized (myLock) {
	    // do something to person
	}
    }
}
