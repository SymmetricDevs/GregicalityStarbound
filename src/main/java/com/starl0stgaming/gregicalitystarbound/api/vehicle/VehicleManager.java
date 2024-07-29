package com.starl0stgaming.gregicalitystarbound.api.vehicle;

import java.util.HashMap;

public class VehicleManager {

    // Config annotation or smth
    private int maxVehicleAmount = 512;

    private HashMap<Integer, Vehicle> vehicleStorage;

    public VehicleManager() {
        this.vehicleStorage = new HashMap<>();
        this.loadVehicles();
    }

    private void loadVehicles() {
        // TODO: load vehicles in world storage
    }

    public void addVehicleToStorage(int id, Vehicle vehicle) {
        this.vehicleStorage.put(id, vehicle);
    }

    public Vehicle getVehicleByID(int id) {
        return this.vehicleStorage.get(id);
    }

    public boolean doesVehicleExist(int id) {
        return this.vehicleStorage.containsKey(id);
    }

    public void removeVehicleFromStorage(int id) {
        this.vehicleStorage.remove(id);
    }
}
