package cn.swift.chapter4;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.swift.annotation.Immutable;
import cn.swift.annotation.ThreadSafe;

/**
 * 4-7 将线程安全委托给ConcurrentHashMap
 */
@ThreadSafe
public class DelegatingVehicleTracker {

    private final ConcurrentHashMap<String, Point> locations;

    private final Map<String, Point> unmodifiableMap;

    public DelegatingVehicleTracker(Map<String, Point> points) {
	locations = new ConcurrentHashMap<>(points);
	unmodifiableMap = Collections.unmodifiableMap(points);
    }

    /**
     * 返回locations的实时拷贝
     */
    public Map<String, Point> getDynamicLocations() {
	return unmodifiableMap;
    }
    
    /**
     * 返回locations的静态拷贝
     */
    public Map<String, Point> getStaticLocations(){
	return Collections.unmodifiableMap(new HashMap<>(locations));
    }

    public Point getLocation(String id) {
	return locations.get(id);
    }

    public void setLocation(String id, int x, int y) {
	if (locations.replace(id, new Point(x, y)) == null) {
	    throw new IllegalStateException("invalid vehicle name: " + id);
	}
    }

    @Immutable
    class Point {
	public final int x;
	public final int y;

	public Point(int x, int y) {
	    this.x = x;
	    this.y = y;
	}
    }
}
