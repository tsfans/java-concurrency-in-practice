package cn.swift.exception;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import cn.swift.annotation.GuardedBy;
import cn.swift.annotation.Sucks;

/**
 * 10-5 在相互协作对象之间的锁顺序死锁
 */
@Sucks
public class Taxi {

    @GuardedBy("this")
    private Point location;

    private Point destination;

    private final Dispatcher dispatcher;

    public Taxi(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public synchronized Point getLocation() {
        return this.location;
    }

    public synchronized void setLocation(Point location) {
        this.location = location;
        if (location.equals(destination)) {
            dispatcher.notifyAvailable(this);
        }
    }

}

class Dispatcher {

    private final Set<Taxi> taxis;

    private final Set<Taxi> availableTaxis;

    public Dispatcher() {
        taxis = new HashSet<>();
        availableTaxis = new HashSet<>();
    }

    public synchronized void notifyAvailable(Taxi taxi) {
        availableTaxis.add(taxi);
    }

    public synchronized Image getImage() {
        Image image = new Image();
        for (Taxi t : taxis) {
            image.drawMarker(t.getLocation());
        }
        return image;
    }

    class Image {

        public void drawMarker(Point location) {

        }

    }

}
