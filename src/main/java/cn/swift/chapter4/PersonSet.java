package cn.swift.chapter4;

import java.util.HashSet;
import java.util.Set;

import cn.swift.annotation.ThreadSafe;

/**
 * 4-2 通过封闭机制来确保线程安全
 */
@ThreadSafe
public class PersonSet {

    private final Set<Person> people = new HashSet<>();

    public synchronized void addPerson(Person p) {
	people.add(p);
    }

    public synchronized boolean isContains(Person p) {
	return people.contains(p);
    }

    class Person {
    }
}
