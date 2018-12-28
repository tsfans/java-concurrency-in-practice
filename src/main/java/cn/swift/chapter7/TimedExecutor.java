package cn.swift.chapter7;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 7-9 在专门的线程中中断线程
 */
public class TimedExecutor {

	private static final ScheduledExecutorService cancelExec = Executors.newScheduledThreadPool(10);

	public static void timedRun(Runnable r, long timeout, TimeUnit timeUnit) throws InterruptedException {

		class RethrowableTask implements Runnable {

			private volatile Throwable t;

			@Override
			public void run() {
				try {
					r.run();
				} catch (Throwable t) {
					this.t = t;
				}
			}

			void rethrow() {
				if (t != null) {
					throw launderThrowable(t);
				}
			}

			private RuntimeException launderThrowable(Throwable cause) {
				if (cause instanceof RuntimeException) {
					return (RuntimeException) cause;
				} else if (cause instanceof Error) {
					throw (Error) cause;
				} else {
					throw new IllegalStateException("Not Unchecked", cause);
				}
			}

		}

		RethrowableTask task = new RethrowableTask();
		final Thread taskThread = new Thread(task);
		taskThread.start();
		cancelExec.schedule(taskThread::interrupt, timeout, timeUnit);
		taskThread.join(timeUnit.toMillis(timeout));
		task.rethrow();
	}
}
