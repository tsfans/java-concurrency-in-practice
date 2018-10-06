package cn.swift.chapter3;

/**
 * 3-4 数绵羊
 * @author Swift Hu
 * @date 2018年9月12日 下午1:43:05
 */
public class SheepCounter {

	volatile boolean asleep;
	
	public void countSheep() {
		while(!asleep) {
			countSomeSheep();
		}
	}

	private void countSomeSheep() {
		System.out.println("Count some sheep...");
	}
}
