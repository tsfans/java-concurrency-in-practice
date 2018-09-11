package cn.swift.chapter2;

/**
 * 2-7 如果内置锁不是可重入的,那么这段代码将发生死锁
 * @author Swift Hu
 * @date 2018年9月11日 下午9:46:09
 */
public class LoggingWidget extends Widget{

	@Override
	public synchronized void doSomething() {
		/**
		 * 每次调用时都要获取Widget上的锁,如果锁不可重入,
		 * 那么调用super.doSomething()时将无法获得Widget上的锁,从而导致死锁
		 * 这说明获取锁的粒度是线程而不是调用,这与pthread(POSIX线程)互斥体的默认加锁行为不同
		 */
		System.out.println("LoggingWidget do something.");
		super.doSomething();
	}
	
}

class Widget{
	
	public synchronized void doSomething() {
		System.out.println("Widget do something.");
	}
}
