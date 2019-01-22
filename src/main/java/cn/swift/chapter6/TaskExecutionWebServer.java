package cn.swift.chapter6;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 6-3 基于线程池的Web服务器
 */
public class TaskExecutionWebServer extends AbstractWebServer{
	
	private static final int N_THREADS = 100;
	
	private static final ExecutorService exec = Executors.newFixedThreadPool(N_THREADS);

	public static void main(String[] args) throws Exception {
		@SuppressWarnings("resource")
        ServerSocket socket = new ServerSocket(80);
		while(true) {
			final Socket connection = socket.accept();
			Runnable task = () -> handleRequest(connection);
			exec.execute(task);
		}
	}
}
