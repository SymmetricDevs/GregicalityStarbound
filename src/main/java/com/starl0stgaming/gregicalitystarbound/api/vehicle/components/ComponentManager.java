package com.starl0stgaming.gregicalitystarbound.api.vehicle.components;

import java.util.HashMap;

public class ComponentManager {

    private HashMap<String, VehicleComponent> componentList;
    private HashMap<Integer, VehicleComponent> vehicleComponentStorage;

    public ComponentManager() {
        componentList = new HashMap<>();
        this.vehicleComponentStorage = new HashMap<>();
    }

    public void registerComponent(String identifier, VehicleComponent component) {
        if(component != null) {
            componentList.put(identifier, component);
        }
    }

    public void unregisterComponent(String identifier) {
        componentList.remove(identifier);
    }

    public VehicleComponent getComponentById(String identifier) {
        return componentList.get(identifier);
    }


}
