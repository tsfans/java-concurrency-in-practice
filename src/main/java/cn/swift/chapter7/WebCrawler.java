package cn.swift.chapter7;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 7-22 使用TrackingExecutor来保存未完成的任务以备后续执行
 */
public abstract class WebCrawler {

	private volatile TrackingExecutor exec;

	private final Set<Url> urlsToCrawl = new HashSet<>();

	public synchronized void start() {
		exec = new TrackingExecutor(Executors.newFixedThreadPool(100));
		for (Url url : urlsToCrawl) {
			submitCrawlTask(url);
		}
		urlsToCrawl.clear();
	}

	public synchronized void stop() throws InterruptedException {
		try {
			saveUnCrawled(exec.shutdownNow());
			if (exec.awaitTermination(5, TimeUnit.MINUTES)) {
				saveUnCrawled(exec.getCancelledTask());
			}
		} finally {
			exec = null;
		}
	}

	protected abstract List<Url> processPage(Url url);

	private void saveUnCrawled(List<Runnable> shutdownNow) {
		for (Runnable task : shutdownNow) {
			urlsToCrawl.add(((CrawlTask) task).getPage());
		}
	}

	private void submitCrawlTask(Url url) {
		exec.execute(new CrawlTask(url));
	}

	class Url {
	}

	class CrawlTask implements Runnable {

		private final Url url;

		public CrawlTask(Url url) {
			this.url = url;
		}

		@Override
		public void run() {
			for (Url link : processPage(url)) {
				if (Thread.currentThread().isInterrupted()) {
					return;
				}
				submitCrawlTask(link);
			}
		}

		public Url getPage() {
			return url;
		}

	}
}
