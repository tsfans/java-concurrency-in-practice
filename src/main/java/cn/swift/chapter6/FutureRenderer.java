package cn.swift.chapter6;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cn.swift.annotation.CouldBeHappier;

/**
 * 6-9 使用Future等待图像下载
 */
@CouldBeHappier
public class FutureRenderer extends SingleThreadRenderer {

	private static final int N_THREADS = 100;

	private static final ExecutorService executor = Executors.newFixedThreadPool(N_THREADS);

	@Override
	public void rederPage(CharSequence source) {
		Callable<List<ImageData>> task = () -> downloadImageData(source);
		Future<List<ImageData>> future = executor.submit(task);

		renderText(source);

		try {
			List<ImageData> imageData = future.get();
			for (ImageData image : imageData) {
				renderImage(image);
			}
		} catch (InterruptedException e) {
			// 重新设置线程的中断状态
			Thread.currentThread().interrupt();
			// 由于不需要结果，所以取消任务
			future.cancel(true);
		} catch (ExecutionException e) {
			throw launderThrowable(e.getCause());
		}
	}
}
