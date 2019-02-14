package cn.swift.chapter5;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;

/**
 * 5-8 磁盘索引中的生产者任务
 */
public class DiskCrawler implements Runnable {

    private final BlockingQueue<File> queue;

    private final FileFilter fileFilter;

    private final File root;

    public DiskCrawler(BlockingQueue<File> queue, File root, final FileFilter filter) {
	this.queue = queue;
	this.root = root;
	this.fileFilter = f -> f.isDirectory() || filter.accept(f);
    }

    @Override
    public void run() {
	try {
	    crawl(root);
	} catch (InterruptedException e) {
	    Thread.currentThread().interrupt();
	}
    }

    private void crawl(File root2) throws InterruptedException {
	File[] entries = root2.listFiles(fileFilter);
	if (entries != null) {
	    for (File file : entries) {
		if (file.isDirectory()) {
		    crawl(file);
		} else if (!alradyIndexed(file)) {
		    queue.put(file);
		}
	    }
	}
    }

    private boolean alradyIndexed(File file) {
	return false;
    }

}
