package cn.swift.chapter8;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

import cn.swift.annotation.ThreadSafe;

/**
 * 8-4 使用Semaphore来控制任务的提交速率
 */
@ThreadSafe
public class BoundedExecutor {

    private final Executor exec;

    private final Semaphore semaphore;

    public BoundedExecutor(Executor exec, int bound) {
        this.exec = exec;
        this.semaphore = new Semaphore(bound);
    }

    public void submitTask(final Runnable command) throws InterruptedException {
        semaphore.acquire();
        try {
            exec.execute(() -> {
                try {
                    command.run();
                } finally {
                    semaphore.release();
                }
            });
        } catch (RejectedExecutionException ignored) {
            semaphore.release();
        }
    }
}
