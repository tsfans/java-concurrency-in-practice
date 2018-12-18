package cn.swift.chapter6;

import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 6-10 使用Completion，使页面元素在下载完成后立即显示出来
 */
public class Render extends SingleThreadRenderer {

	private final ExecutorService executor;

	public Render(ExecutorService executor) {
		super();
		this.executor = executor;
	}

	@Override
	public void rederPage(CharSequence source) {
		final List<ImageInfo> imageInfo = scanForImageInfo(source);
		CompletionService<ImageData> completionService = new ExecutorCompletionService<>(executor);
		imageInfo.forEach(image -> completionService.submit(image::downloadImage));

		renderText(source);

		try {
			for (int i = 0; i < imageInfo.size(); i++) {
				Future<ImageData> future = completionService.take();
				renderImage(future.get());
			}
		} catch (InterruptedException e) {
			// 重新设置线程的中断状态
			Thread.currentThread().interrupt();
		} catch (ExecutionException e) {
			throw launderThrowable(e.getCause());
		}
	}
	
}
