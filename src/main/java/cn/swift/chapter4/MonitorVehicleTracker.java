package cn.swift.chapter4;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cn.swift.annotation.NotThreadSafe;
import cn.swift.annotation.ThreadSafe;

/**
 * 4-4 基于监视器模式的车辆追踪
 */
@ThreadSafe
public class MonitorVehicleTracker {

    private final Map<String, MutablePoint> locations;

    public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
	this.locations = deepCopy(locations);
    }

    public synchronized MutablePoint getLocation(String id) {
	MutablePoint mp = locations.get(id);
	return mp == null ? null : new MutablePoint(mp);
    }

    public synchronized Map<String, MutablePoint> getLocations() {
	return deepCopy(locations);
    }

    private Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> m) {
	Map<String, MutablePoint> result = new HashMap<>();
	for (String key : m.keySet()) {
	    result.put(key, new MutablePoint(m.get(key)));
	}
	return Collections.unmodifiableMap(result);
    }

    @NotThreadSafe
    class MutablePoint {
	public int x;
	public int y;

	public MutablePoint() {
	    x = 0;
	    y = 0;
	}

	public MutablePoint(MutablePoint mp) {
	    this.x = mp.x;
	    this.y = mp.y;
	}
    }
}
