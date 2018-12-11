package cn.swift.chapter3;

import java.awt.Event;
import java.util.EventListener;

/**
 * 3-7 隐式地使this逸出（不要这么做）
 */
public class ThisEscape {

    public ThisEscape(EventSource es) {
	es.registerListener(new EventListener() {
	    @SuppressWarnings("unused")
	    public void onEvent(Event e) {
		doSomething(e);
	    }
	});
    }

    private void doSomething(Event e) {
    }

    class EventSource {
	public void registerListener(EventListener el) {
	}
    }
}
