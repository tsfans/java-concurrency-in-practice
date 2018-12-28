package cn.swift.chapter7;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import cn.swift.annotation.CouldBeHappier;

/**
 * 7-13 不支持关闭的生产者-消费者日志服务
 */
@CouldBeHappier
public class LogWriter {

	private final BlockingQueue<String> queue;

	private final LoggerThread logger;

	public LogWriter(PrintWriter writer) {
		queue = new LinkedBlockingQueue<>(100);
		logger = new LoggerThread(writer);
	}

	public void start() {
		logger.start();
	}

	public void log(String log) throws InterruptedException {
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
				writer.println(queue.take());
			} catch (InterruptedException e) {
				// ignored
			} finally {
				writer.close();
			}
		}
	}

}
