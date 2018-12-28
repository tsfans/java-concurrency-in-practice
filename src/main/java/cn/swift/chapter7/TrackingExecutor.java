package cn.swift.chapter7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 7-21 在ExecutorService中跟踪关闭后被取消的任务
 * 存在“误报”问题，可能会导致任务执行两次
 */
public class TrackingExecutor extends AbstractExecutorService {

	private final ExecutorService exec;

	private final Set<Runnable> taskCancelledAtShutdown = Collections.synchronizedSet(new HashSet<>());

	public TrackingExecutor(ExecutorService exec) {
		this.exec = exec;
	}

	public List<Runnable> getCancelledTask() {
		if (!exec.isTerminated()) {
			throw new IllegalStateException("Executor is not terminated");
		}
		return new ArrayList<>(taskCancelledAtShutdown);
	}

	@Override
	public void execute(Runnable command) {
		exec.execute(() -> {
			try {
				command.run();
			} finally {
				if (isShutdown() && Thread.currentThread().isInterrupted()) {
					taskCancelledAtShutdown.add(command);
				}
			}
		});

	}

	@Override
	public void shutdown() {
		exec.shutdown();
	}

	@Override
	public List<Runnable> shutdownNow() {
		return exec.shutdownNow();
	}

	@Override
	public boolean isShutdown() {
		return exec.isShutdown();
	}

	@Override
	public boolean isTerminated() {
		return exec.isTerminated();
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return exec.awaitTermination(timeout, unit);
	}

}
