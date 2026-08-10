package com.skd.vehiclery.item;

import com.skd.vehiclery.automobile.AutomobileWheel;

public class AutomobileWheelItem extends AutomobileComponentItem.Dynamic<AutomobileWheel> {
    public AutomobileWheelItem(Properties settings) {
        super(settings, "wheel", AutomobileWheel.REGISTRY, VehicleryItems.COMPONENT_WHEEL, AutomobileWheel.EMPTY);
    }
}
