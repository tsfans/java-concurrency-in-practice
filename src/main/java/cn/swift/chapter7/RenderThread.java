package cn.swift.chapter7;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 7-11 通过改写interrupt方法将非标准的操作封装在Thread中
 */
public class RenderThread extends Thread {

	private final Socket socket;

	private final InputStream is;

	public RenderThread(Socket socket) throws IOException {
		this.socket = socket;
		is = socket.getInputStream();
	}

	@Override
	public void run() {
		try {
			byte[] buf = new byte[1024];
			while (true) {
				int count = is.read(buf);
				if (count < 0) {
					break;
				} else if (count > 0) {
					processBuffer(buf, count);
				}
			}
		} catch (IOException e) {
			// 允许线程退出
		}
	}

	private void processBuffer(byte[] buf, int count) {

	}

	@Override
	public void interrupt() {
		try {
			socket.close();
		} catch (IOException e) {
			// ignored
		} finally {
			super.interrupt();
		}
	}
}
