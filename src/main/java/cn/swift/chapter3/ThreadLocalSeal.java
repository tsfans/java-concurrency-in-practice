package cn.swift.chapter3;

import java.sql.Connection;

/**
 * 3-10 使用ThreadLocal维持线程封闭性
 */
public class ThreadLocalSeal {

    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();
    
    public static Connection getConnection() {
	return connectionHolder.get();
    }
}
