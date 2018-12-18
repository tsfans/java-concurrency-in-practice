package cn.swift.chapter6;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.Timer;
import java.util.TimerTask;

import cn.swift.annotation.CouldBeHappier;

/**
 * 6-7 错误的Timer行为
 */
@CouldBeHappier
public class OutOfTime {

	public static void main(String[] args) throws Exception {
		Timer timer = new Timer();
		timer.schedule(new ThrowTask(), 1);
		SECONDS.sleep(1);
		timer.schedule(new ThrowTask(), 1);
		SECONDS.sleep(5);

	}

	static class ThrowTask extends TimerTask {
		@Override
		public void run() {
			throw new RuntimeException();
		}
	}
}
