package cn.swift.chapter7;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 7-20 使用私有的Executor，且该Executor的生命周期受方法调用控制
 */
public class MailChecker {

	boolean checkMail(Set<String> hosts, long timeout, TimeUnit unit) throws InterruptedException {
		final ExecutorService exec = Executors.newCachedThreadPool();
		final AtomicBoolean hasNewMail = new AtomicBoolean(false);
		try {
			for (final String host : hosts) {
				exec.execute(() -> {
					if (checkMail(host)) {
						hasNewMail.set(true);
					}
				});
			}
		} finally {
			exec.shutdown();
			exec.awaitTermination(timeout, unit);
		}
		return hasNewMail.get();
	}

	boolean checkMail(String host) {
		if (host != null) {
			return true;
		}
		return false;
	}
}
