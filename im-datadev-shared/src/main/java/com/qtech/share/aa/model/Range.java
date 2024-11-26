package com.qtech.share.aa.model;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/20 11:41:47
 * desc   :  这个实现允许你灵活地指定要比较的属性以及它们的范围，同时保持代码的可读性和可维护性。
 */


public class Range<T extends Comparable<T>> {
    private T min;
    private T max;

    public Range(T min, T max) {
        this.min = min;
        this.max = max;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }
}

