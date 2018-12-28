package cn.swift.chapter7;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.swift.annotation.Sucks;

/**
 * 7-8 在外部线程中安排中断
 */
@Sucks
public class TimedExecutor1 {

	private static final ScheduledExecutorService cancelExec = Executors.newScheduledThreadPool(10);

	public static void timedRun(Runnable r, long timeout, TimeUnit timeUnit) {
		final Thread taskThread = Thread.currentThread();
		cancelExec.schedule(taskThread::interrupt, timeout, timeUnit);
		r.run();
	}
}
