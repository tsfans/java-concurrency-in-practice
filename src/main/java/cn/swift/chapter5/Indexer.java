package cn.swift.chapter5;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * 5-8 磁盘索引中的消费者任务
 */
public class Indexer implements Runnable {

    private final BlockingQueue<File> queue;

    public Indexer(BlockingQueue<File> queue) {
	this.queue = queue;
    }

    @Override
    public void run() {
	try {
	    while (true) {
		indexFile(queue.take());
	    }
	} catch (InterruptedException e) {
	    Thread.currentThread().interrupt();
	}
    }

    private void indexFile(File take) {
	// Index file
    }

}
