package com.skd.vehiclery.item;

import com.skd.vehiclery.automobile.AutomobileEngine;

public class AutomobileEngineItem extends AutomobileComponentItem.Dynamic<AutomobileEngine> {
    public AutomobileEngineItem(Properties settings) {
        super(settings, "engine", AutomobileEngine.REGISTRY, VehicleryItems.COMPONENT_ENGINE, AutomobileEngine.EMPTY);
    }
}
