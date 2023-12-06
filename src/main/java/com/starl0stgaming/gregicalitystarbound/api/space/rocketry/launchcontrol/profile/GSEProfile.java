package com.starl0stgaming.gregicalitystarbound.api.space.rocketry.launchcontrol.profile;

import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.GSE.GSEComputer;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.fuel.network.FuelNetwork;

public class GSEProfile {

    private GSEComputer gseComputer;

    private int fuelFlowRate;
    private FuelNetwork fuelNetwork;

    public GSEProfile(GSEComputer gseComputer) {
        this.fuelFlowRate = 500; //measured in Liters / tick, should be dynamic, but that will come with a fuel logic overhaul
        this.gseComputer = gseComputer;

        this.fuelNetwork = this.gseComputer.getFuelNetwork();
    }

    public GSEComputer getGseComputer() {
        return gseComputer;
    }

    public void setGseComputer(GSEComputer gseComputer) {
        this.gseComputer = gseComputer;
    }

    public int getFuelFlowRate() {
        return fuelFlowRate;
    }

    public void setFuelFlowRate(int fuelFlowRate) {
        this.fuelFlowRate = fuelFlowRate;
    }

    public FuelNetwork getFuelNetwork() {
        return fuelNetwork;
    }

    public void setFuelNetwork(FuelNetwork fuelNetwork) {
        this.fuelNetwork = fuelNetwork;
    }
}
