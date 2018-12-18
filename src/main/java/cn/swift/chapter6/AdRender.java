package cn.swift.chapter6;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 6-10 在指定时间内获取广告信息
 */
public class AdRender {

	private static final long TIME_BUDGET = 1000;

	private static final Ad DEFAULT_AD = new Ad();

	private static final ExecutorService exec = Executors.newCachedThreadPool();

	public Page renderPageWithAd() throws InterruptedException {
		long endNanos = System.nanoTime() + TIME_BUDGET;
		Future<Ad> task = exec.submit(new FetchAdTask());
		// 在等待广告的同时显示页面
		Page page = renderBody();
		Ad ad;
		try {
			// 只等待指定的时间
			long leftTime = endNanos - System.nanoTime();
			ad = task.get(leftTime, TimeUnit.NANOSECONDS);
		} catch (ExecutionException e) {
			ad = DEFAULT_AD;
		} catch (TimeoutException e) {
			ad = DEFAULT_AD;
			task.cancel(true);
		}
		page.setAd(ad);
		return page;
	}

	private Page renderBody() {
		// 渲染页面
		return new Page();
	}

	static class Ad {
	}

	static class Page {

		void setAd(Ad ad) {
		}
	}

	static class FetchAdTask implements Callable<Ad> {

		@Override
		public Ad call() throws Exception {
			return new Ad();
		}

	}
}
