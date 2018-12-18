package cn.swift.chapter6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 6-17 在预定时间内请求旅游报价
 */
public class TravelQuoteFetcher {

	private static final ExecutorService exec = Executors.newCachedThreadPool();

	public List<TravelQuote> getRankedTravelQuote(TravelInfo travelInfo, Set<TravelCompany> companies,
			Comparator<TravelQuote> ranking, long time, TimeUnit unit) throws InterruptedException {
		List<QuoteTask> tasks = new ArrayList<>(companies.size());
		for (TravelCompany company : companies) {
			tasks.add(new QuoteTask(company, travelInfo));
		}
		List<Future<TravelQuote>> futures = exec.invokeAll(tasks, time, unit);
		List<TravelQuote> quotes = new ArrayList<>(tasks.size());
		Iterator<QuoteTask> it = tasks.iterator();
		for (Future<TravelQuote> future : futures) {
			// 任务集合与future集合是一一对应的，invokeAll按照任务集合中迭代器的顺序将future添加到返回的集合中
			QuoteTask task = it.next();
			try {
				quotes.add(future.get());
			} catch (ExecutionException e) {
				quotes.add(task.getFailureQuote(e.getCause()));
			} catch (CancellationException e) {
				quotes.add(task.getTimeoutQuote(e));
			}
		}
		Collections.sort(quotes, ranking);
		return quotes;
	}

	private class QuoteTask implements Callable<TravelQuote> {

		private final TravelCompany company;

		private final TravelInfo travelInfo;

		public QuoteTask(TravelCompany company, TravelInfo travelInfo) {
			this.company = company;
			this.travelInfo = travelInfo;
		}

		public TravelQuote getTimeoutQuote(CancellationException e) {
			return new TravelQuote();
		}

		public TravelQuote getFailureQuote(Throwable cause) {
			return new TravelQuote();
		}

		@Override
		public TravelQuote call() throws Exception {
			return company.solicitQuote(travelInfo);
		}
	}

	class TravelCompany {
		TravelQuote solicitQuote(TravelInfo travelInfo) {
			return new TravelQuote();
		}
	}

	class TravelInfo {

	}

	class TravelQuote {

	}
}
