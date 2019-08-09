package cn.swift.utils;

public class RNGUtils {

    private RNGUtils() {}

    public static int xorShift(int y) {
        y ^= (y << 6);
        y ^= (y >>> 21);
        y ^= (y << 7);
        return y;
    }
}
