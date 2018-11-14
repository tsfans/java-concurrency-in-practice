package cn.swift.chapter4;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.swift.annotation.ThreadSafe;

/**
 * 4-12 安全发布底层状态的车辆追踪器
 */
@ThreadSafe
public class PublishingVehicleTracker {

    private final Map<String, SafePoint> locations;
    private final Map<String, SafePoint> unmodifiableMap;

    public PublishingVehicleTracker(Map<String, SafePoint> locations) {
	this.locations = new ConcurrentHashMap<>(locations);
	unmodifiableMap = Collections.unmodifiableMap(locations);
    }

    public Map<String, SafePoint> getLocations() {
	return unmodifiableMap;
    }

    public SafePoint getLocation(String id) {
	return locations.get(id);
    }

    public void setLocation(String id, int x, int y) {
	if (!locations.containsKey(id)) {
	    throw new IllegalArgumentException("invalid vehicle name: " + id);
	}
	locations.get(id).set(x, y);
    }

    /**
     * 4-11 线程安全且可变的Point
     */
    @ThreadSafe
    class SafePoint {
	private int x;
	private int y;

	private SafePoint(int[] a) {
	    this(a[0], a[1]);
	}

	public SafePoint(SafePoint sp) {
	    this(sp.get());
	}

	public SafePoint(int x, int y) {
	    this.x = x;
	    this.y = y;
	}

	public synchronized int[] get() {
	    return new int[] { x, y };
	}

	public synchronized void set(int x, int y) {
	    this.x = x;
	    this.y = y;
	}
    }
}
