package cn.swift.exception;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import cn.swift.annotation.GuardedBy;
import cn.swift.annotation.ThreadSafe;

/**
 * 10-6 通过公开调用来避免在相互协作的对象之间产生死锁
 */
@ThreadSafe
public class ThreadSafeTaxi {

    @GuardedBy("this")
    private Point location;

    private Point destination;

    private final ThreadSafeDispatcher dispatcher;

    public ThreadSafeTaxi(ThreadSafeDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public synchronized Point getLocation() {
        return this.location;
    }

    public void setLocation(Point location) {
        boolean reachedDestination;
        synchronized (this) {
            this.location = location;
            reachedDestination = location.equals(destination);
        }
        if (reachedDestination) {
            dispatcher.notifyAvailable(this);
        }
    }

}

class ThreadSafeDispatcher {

    private final Set<ThreadSafeTaxi> taxis;

    private final Set<ThreadSafeTaxi> availableTaxis;

    public ThreadSafeDispatcher() {
        taxis = new HashSet<>();
        availableTaxis = new HashSet<>();
    }

    public synchronized void notifyAvailable(ThreadSafeTaxi taxi) {
        availableTaxis.add(taxi);
    }

    public Image getImage() {
        Set<ThreadSafeTaxi> copy;
        synchronized (this) {
            copy = new HashSet<>(taxis);
        }
        Image image = new Image();
        for (ThreadSafeTaxi t : copy) {
            image.drawMarker(t.getLocation());
        }
        return image;
    }

    class Image {

        public void drawMarker(Point location) {

        }

    }
}
