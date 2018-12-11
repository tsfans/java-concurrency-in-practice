package cn.swift.chapter5.cache;

public abstract class AbstractMemorizer<A, V> implements Computable<A, V> {

    protected RuntimeException launderThrowable(Throwable cause) {
	if (cause instanceof RuntimeException) {
	    return (RuntimeException) cause;
	} else if (cause instanceof Error) {
	    throw (Error) cause;
	} else {
	    throw new IllegalStateException("Not Unchecked", cause);
	}
    }
}
