package cn.swift.chapter7;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 7-10 通过Future来取消任务
 */
public class TimedExecutor2 {

	private static final ExecutorService taskExec = Executors.newFixedThreadPool(10);
	
	public static void timedRun(Runnable r, long timeout, TimeUnit timeUnit) throws InterruptedException {
		Future<?> task = taskExec.submit(r);
		try {
			task.get(timeout, timeUnit);
		} catch (TimeoutException e) {
			// 接下来任务将被取消
		} catch (ExecutionException e) {
			// 如果在任务中抛出了异常， 那么重新抛出该异常
			throw launderThrowable(e.getCause());
		} finally {
			// 如果任务已经结束，那么取消操作也不会带来任何影响；如果任务正在进行，那么将被中断
			task.cancel(true);
		}
		
	}
	
	private static RuntimeException launderThrowable(Throwable cause) {
		if (cause instanceof RuntimeException) {
			return (RuntimeException) cause;
		} else if (cause instanceof Error) {
			throw (Error) cause;
		} else {
			throw new IllegalStateException("Not Unchecked", cause);
		}
	}
}
