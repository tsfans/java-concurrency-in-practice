package cn.swift.chapter6;

import java.util.ArrayList;
import java.util.List;

import cn.swift.annotation.CouldBeHappier;

/**
 * 6-8 串行地渲染页面元素
 */
@CouldBeHappier
public class SingleThreadRenderer {

	public void rederPage(CharSequence source) {
		renderText(source);
		List<ImageData> imageData = downloadImageData(source);
		for (ImageData image : imageData) {
			renderImage(image);
		}
	}

	protected List<ImageData> downloadImageData(CharSequence source) {
		List<ImageData> imageDatas = new ArrayList<>();
		for (ImageInfo image : scanForImageInfo(source)) {
			imageDatas.add(image.downloadImage());
		}
		return imageDatas;
	}

	protected void renderImage(ImageData image) {
		// render image
	}

	protected List<ImageInfo> scanForImageInfo(CharSequence source) {
		return new ArrayList<>();
	}

	protected void renderText(CharSequence source) {
		// render text
	}

	protected RuntimeException launderThrowable(Throwable cause) {
		if (cause instanceof RuntimeException) {
			return (RuntimeException) cause;
		} else if (cause instanceof Error) {
			throw (Error) cause;
		} else {
			throw new IllegalStateException("Not Unchecked", cause);
		}
	}

	class ImageData {
	}

	class ImageInfo {

		public ImageData downloadImage() {
			return new ImageData();
		}
	}
}
