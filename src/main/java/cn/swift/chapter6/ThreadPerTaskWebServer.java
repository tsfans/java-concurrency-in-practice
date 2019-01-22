package cn.swift.chapter6;

import java.net.ServerSocket;
import java.net.Socket;

import cn.swift.annotation.Sucks;

/**
 * 6-2 在Web服务器中为每个请求新建一个线程
 */
@Sucks
public class ThreadPerTaskWebServer extends AbstractWebServer {

	public static void main(String[] args) throws Exception {
		@SuppressWarnings("resource")
        ServerSocket socket = new ServerSocket(80);
		while (true) {
			final Socket connection = socket.accept();
			Runnable task = () -> handleRequest(connection);
			new Thread(task).start();
		}
	}
}
