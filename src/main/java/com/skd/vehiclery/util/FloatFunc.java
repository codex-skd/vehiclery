package com.skd.vehiclery.util;

@FunctionalInterface
public interface FloatFunc<V> {
    float apply(V val);
}
