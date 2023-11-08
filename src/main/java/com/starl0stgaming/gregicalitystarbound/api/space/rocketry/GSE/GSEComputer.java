package com.starl0stgaming.gregicalitystarbound.api.space.rocketry.GSE;

import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.fuel.FuelTank;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.fuel.network.FuelNetwork;

public class GSEComputer {

    //TODO: should be expandable.
    private FuelNetwork fuelNetwork;

    private int fuelFlowRate;



    public GSEComputer() {
        this.fuelNetwork = new FuelNetwork(3);
    }

    public FuelNetwork getFuelNetwork() {
        return fuelNetwork;
    }

    public int getFuelFlowRate() {
        return fuelFlowRate;
    }

    public void setFuelFlowRate(int fuelFlowRate) {
        this.fuelFlowRate = fuelFlowRate;
    }
}
