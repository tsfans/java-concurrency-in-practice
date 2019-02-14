package cn.swift.chapter11;

import java.util.Set;

import cn.swift.annotation.GuardedBy;
import cn.swift.annotation.ThreadSafe;

/**
 * 11-6 对锁进行分解
 */
@ThreadSafe
public class ServerStatus {

    @GuardedBy("this")
    private final Set<String> users;

    @GuardedBy("this")
    private final Set<String> queries;

    public ServerStatus(Set<String> users, Set<String> queries) {
        this.users = users;
        this.queries = queries;
    }

    public synchronized void addUser(String u) {
        users.add(u);
    }

    public synchronized void addQuery(String q) {
        queries.add(q);
    }

    public synchronized void removeUser(String u) {
        users.remove(u);
    }

    public synchronized void removeQuery(String q) {
        queries.remove(q);
    }
}
