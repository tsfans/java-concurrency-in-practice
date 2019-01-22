package cn.swift.chapter6;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import org.omg.CORBA.Request;

/**
 * 6-6 支持关闭操作的Web服务器
 */
public class LifecycleWebServer extends AbstractWebServer {

	private static final int N_THREADS = 100;

	private static final ExecutorService exec = Executors.newFixedThreadPool(N_THREADS);

	public static void main(String[] args) throws Exception {
		@SuppressWarnings("resource")
        ServerSocket socket = new ServerSocket(80);
		while(!exec.isShutdown()) {
			try {
				final Socket connection = socket.accept();
				Runnable task = () -> handleRequest(connection);
				exec.execute(task);
			}catch(RejectedExecutionException e) {
				if(!exec.isShutdown()) {
					System.out.println("task submission rejected.");
				}
			}
		}
	}
	
	protected static void handleRequest(Socket connection) {
		Request req = readRequest(connection);
		if(isShoutdownRequest(req)) {
			stop();
		} else {
			dispatchRequest(req);
		}
	}

	private static void dispatchRequest(Request req) {
		// do something
	}

	private static void stop() {
		exec.shutdown();
	}

	private static boolean isShoutdownRequest(Request req) {
		return false;
	}

	private static Request readRequest(Socket connection) {
		return null;
	}

}
