package cn.swift.chapter5;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 5-9 启动桌面搜索
 */
public class DesktopSearcher {

    private static final int BOUND = 10;

    private static final int N_CONSUMER = Runtime.getRuntime().availableProcessors();

    public static void startIndex(File[] roots) {
	BlockingQueue<File> fileQueue = new LinkedBlockingDeque<>(BOUND);
	FileFilter fileFilter = (f) -> true;
	for (File root : roots) {
	    new Thread(new DiskCrawler(fileQueue, root, fileFilter)).start();
	}
	for (int i = 0; i < N_CONSUMER; i++) {
	    new Thread(new Indexer(fileQueue)).start();
	}

    }
}
