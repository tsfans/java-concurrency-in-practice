package cn.swift.chapter3;

import java.awt.Event;
import java.util.EventListener;

import cn.swift.chapter3.ThisEscape.EventSource;

/**
 * 3-8 使用工厂方法来防止this引用在构造过程中逸出
 */
public class SafeListener {

    private final EventListener eventListener;
    
    private SafeListener() {
	eventListener = new EventListener() {
	    @SuppressWarnings("unused")
	    public void onEvent(Event e) {
		doSomething(e);
	    }
	};
    }

    public static SafeListener newInstance(EventSource es) {
	SafeListener sl = new SafeListener();
	es.registerListener(sl.eventListener);
	return sl;
    }
    
    private void doSomething(Event e) {
	
    }
}
