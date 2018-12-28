package cn.swift.chapter7;

import java.util.concurrent.BlockingQueue;

public class UnCancellableTask {

	/**
	 * 7-7 不可取消的任务在取消时退出中断
	 */
	public Task getNextTask(BlockingQueue<Task> queue) {
		boolean interrupted = false;
		while (true) {
			try {
				return queue.take();
			} catch (InterruptedException e) {
				interrupted = true;
			} finally {
				if (interrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	class Task {}
}
