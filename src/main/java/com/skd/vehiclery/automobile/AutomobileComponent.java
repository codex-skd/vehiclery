package com.skd.vehiclery.automobile;

import com.skd.vehiclery.util.SimpleMapContentRegistry;

public interface AutomobileComponent<T extends AutomobileComponent<T>> extends SimpleMapContentRegistry.Identifiable, StatContainer<T> {
    boolean isEmpty();
}
