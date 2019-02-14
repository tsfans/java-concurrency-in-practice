package cn.swift.chapter11;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import cn.swift.annotation.GuardedBy;
import cn.swift.annotation.ThreadSafe;

/**
 * 11-5 减少锁的持有时间
 */
@ThreadSafe
public class BetterAttributeStore {

    @GuardedBy("this")
    private final Map<String, String> attributes = new HashMap<>();

    public boolean userLocationMatches(String name, String regexp) {
        String key = "users." + name + ".location";
        String location;
        synchronized (this) {
            location = attributes.get(key);
        }
        if (location == null) {
            return false;
        }
        return Pattern.matches(regexp, location);
    }
}
