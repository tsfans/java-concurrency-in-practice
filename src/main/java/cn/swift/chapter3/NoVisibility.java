package cn.swift.chapter3;

/**
 * 3-1 在没有同步的情况下共享变量(不要这么做)
 * @author Swift Hu
 * @date 2018年9月12日 下午1:22:01
 */
public class NoVisibility {

	private static boolean ready;
	
	private static int number;
	
	private static class ReaderThread extends Thread{
		@Override
		public void run() {
			while(!ready) {
				Thread.yield();
				System.out.println(number);
			}
		}
	}
	
	public static void main(String[] args) {
		new ReaderThread().start();
		number = 24;
		ready = true;
	}
}
