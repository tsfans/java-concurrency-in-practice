package cn.swift.chapter6;

import java.net.ServerSocket;
import java.net.Socket;

import cn.swift.annotation.CouldBeHappier;

/**
 * 6-1 串行的Web服务器
 */
@CouldBeHappier
public class SingleThreadWebServer extends AbstractWebServer{

	public static void main(String[] args) throws Exception {
		@SuppressWarnings("resource")
        ServerSocket socket = new ServerSocket(80);
		while(true) {
			Socket connection = socket.accept();
			handleRequest(connection);
		}
	}
}
