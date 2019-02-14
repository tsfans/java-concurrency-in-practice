package cn.swift.chapter11;

import java.util.concurrent.BlockingQueue;

/**
 * 11-1 对任务队列的串行访问
 */
public class WorkThread extends Thread {

    private BlockingQueue<Runnable> queue;

    public WorkThread(BlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Runnable r = queue.take();
                r.run();
            } catch (InterruptedException e) {
                // 允许线程退出
                break;
            }

        }
    }
}
