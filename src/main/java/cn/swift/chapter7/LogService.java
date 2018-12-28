package cn.swift.chapter7;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import cn.swift.annotation.GuardedBy;

/**
 * 7-15 向LoggerWriter添加可靠的取消操作
 */
public class LogService {

	private final BlockingQueue<String> queue;

	private final LoggerThread logger;

	@GuardedBy("this")
	private boolean isShoutdown;

	@GuardedBy("this")
	private int reservations;

	public LogService(PrintWriter writer) {
		this.queue = new LinkedBlockingDeque<>(100);
		this.logger = new LoggerThread(writer);
	}

	public void start() {
		logger.start();
	}

	public void stop() {
		synchronized (this) {
			isShoutdown = true;
		}
		logger.interrupt();
	}

	public void log(String log) throws InterruptedException {
		synchronized (this) {
			if (isShoutdown) {
				throw new IllegalArgumentException("logger is shouting down...");
			}
			++reservations;
		}
		queue.put(log);
	}

	private class LoggerThread extends Thread {

		private final PrintWriter writer;

		public LoggerThread(PrintWriter writer) {
			this.writer = writer;
		}

		@Override
		public void run() {
			try {
				while (true) {
					synchronized (LogService.this) {
						if (isShoutdown && reservations == 0) {
							break;
						}
					}
					String log = queue.take();
					synchronized (LogService.this) {
						--reservations;
					}
					writer.println(log);
				}
			} catch (InterruptedException ignored) {
				/* retry **/
			} finally {
				writer.close();
			}
		}
	}
}
