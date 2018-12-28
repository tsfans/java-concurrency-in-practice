package cn.swift.chapter7;

import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 7-16 使用ExecutorService的日志服务
 */
public class LogService1 {

	private static final long TIME_OUT = 1000;

	private final ExecutorService exec = Executors.newSingleThreadExecutor();

	private final PrintWriter writer;

	public LogService1(PrintWriter writer) {
		this.writer = writer;
	}

	public void stop() throws InterruptedException {
		try {
			exec.shutdown();
			exec.awaitTermination(TIME_OUT, TimeUnit.MILLISECONDS);
		} finally {
			writer.close();
		}
	}

	public void log(String log) {
		try {
			exec.execute(new WriterTask(log));
		} catch (RejectedExecutionException ignored) {
			// ignored
		}
	}

	class WriterTask implements Runnable {

		private final String log;

		public WriterTask(String log) {
			this.log = log;
		}

		@Override
		public void run() {
			writer.println(log);
		}
	}
}
