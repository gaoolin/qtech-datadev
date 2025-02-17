package com.qtech.share.aa.model;

import java.util.Objects;

/**
 * author :  gaozhilin
 * email  :  gaoolin@gmail.com
 * date   :  2024/05/20 11:41:47
 * desc   :  这个实现允许你灵活地指定要比较的属性以及它们的范围，同时保持代码的可读性和可维护性。
 */
public class Range<T extends Comparable<T>> {
    private final T min;
    private final T max;

    public Range(T min, T max) {
        if (min == null || max == null) {
            throw new IllegalArgumentException("Range boundaries cannot be null");
        }
        if (min.compareTo(max) > 0) {
            throw new IllegalArgumentException("Min value cannot be greater than max value");
        }
        this.min = min;
        this.max = max;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    /**
     * 检查给定的值是否在范围内（包括边界）
     *
     * @param value 要检查的值
     * @return 如果值在范围内返回 true，否则返回 false
     */
    public boolean contains(T value) {
        if (value == null) {
            return false;
        }
        return value.compareTo(min) >= 0 && value.compareTo(max) <= 0;
    }

    @Override
    public String toString() {
        return "[" + min + ", " + max + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Range<?> range = (Range<?>) o;
        return Objects.equals(min, range.min) && Objects.equals(max, range.max);
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }
}