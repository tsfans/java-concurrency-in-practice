package cn.swift.chapter11;

import java.util.Set;

import cn.swift.annotation.GuardedBy;
import cn.swift.annotation.ThreadSafe;

/**
 * 11-7 使用锁分段技术改写ServerStatus
 */
@ThreadSafe
public class ServerStatus2 {

    @GuardedBy("users")
    private final Set<String> users;

    @GuardedBy("queries")
    private final Set<String> queries;

    public ServerStatus2(Set<String> users, Set<String> queries) {
        this.users = users;
        this.queries = queries;
    }

    public void addUser(String u) {
        synchronized (users) {
            users.add(u);
        }
    }

    public void addQuery(String q) {
        synchronized (queries) {
            queries.add(q);
        }
    }

    public void removeUser(String u) {
        synchronized (users) {
            users.remove(u);
        }
    }

    public void removeQuery(String q) {
        synchronized (queries) {
            queries.remove(q);
        }
    }
}
