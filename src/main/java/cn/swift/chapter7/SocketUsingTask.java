package cn.swift.chapter7;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

import cn.swift.annotation.GuardedBy;

/**
 * 7-12 通过newTaskFor将非标准的取消操作封装在一个任务中
 */
public class SocketUsingTask<T> implements CancellableTask<T> {

	@GuardedBy("this")
	private Socket socket;

	@Override
	public T call() throws Exception {
		return null;
	}

	@Override
	public synchronized void cancel() {
		try {
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			// ignored
		}
	}

	@Override
	public RunnableFuture<T> newTask() {
		return new FutureTask<T>(this) {
			@SuppressWarnings("finally")
			@Override
			public boolean cancel(boolean mayInterruptIfRunning) {
				try {
					SocketUsingTask.this.cancel();
				} catch (Exception e) {
					// ignored
				} finally {
					return super.cancel(mayInterruptIfRunning);
				}
			}
		};
	}

}
