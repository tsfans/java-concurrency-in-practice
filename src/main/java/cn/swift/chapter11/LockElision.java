package cn.swift.chapter11;

import java.util.List;
import java.util.Vector;

import cn.swift.annotation.Sucks;

public class LockElision {

    @Sucks
    public void sucks() {
        /**
         * 11-2 没有作用的同步
         */
        synchronized (new Object()) {
            // do something
        }
    }

    /**
     * 11-3 可通过锁消除优化去掉的锁获取操作
     */
    public String getStoogeName() {
        List<String> str = new Vector<>();
        str.add("abc");
        str.add("def");
        str.add("hij");
        return str.toString();
    }
}
