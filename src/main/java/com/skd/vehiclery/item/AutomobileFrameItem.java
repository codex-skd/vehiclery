package com.skd.vehiclery.item;

import com.skd.vehiclery.automobile.AutomobileFrame;

public class AutomobileFrameItem extends AutomobileComponentItem.Dynamic<AutomobileFrame> {
    public AutomobileFrameItem(Properties settings) {
        super(settings, "frame", AutomobileFrame.REGISTRY, VehicleryItems.COMPONENT_FRAME, AutomobileFrame.EMPTY);
    }
}
