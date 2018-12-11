package cn.swift.chapter5;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import cn.swift.exception.DataLoadException;

/**
 * 5-12 使用FutureTask来加在稍后需要的数据
 */
public class PreLoader {

    private final FutureTask<ProductInfo> futureTask = new FutureTask<>(new Callable<ProductInfo>() {
	@Override
	public ProductInfo call() throws Exception {
	    System.out.println("Load product info from database...");
	    return new ProductInfo();
	}
    });

    private final Thread thread = new Thread(futureTask);

    public void start() {
	thread.start();
    }

    public ProductInfo get() throws DataLoadException, InterruptedException {
	try {
	    return futureTask.get();
	} catch (ExecutionException e) {
	    e.printStackTrace();
	    Throwable cause = e.getCause();
	    if (cause instanceof DataLoadException) {
		throw (DataLoadException) cause;
	    } else {
		throw launderThrowable(cause);
	    }
	}
    }

    /**
     * 5-13 强制将未检查的Throwable转化为RuntimeException
     * 1.如果throwable是error，直接抛出 </br>
     * 2.如果是RuntimeException，直接返回 </br>
     * 3.其他异常抛出IllegalStateException表示这是一个逻辑错误
     */
    private RuntimeException launderThrowable(Throwable cause) {
	if (cause instanceof RuntimeException) {
	    return (RuntimeException) cause;
	} else if (cause instanceof Error) {
	    throw (Error) cause;
	} else {
	    throw new IllegalStateException("Not Unchecked", cause);
	}
    }

    class ProductInfo {

    }
}
