package com.starl0stgaming.gregicalitystarbound.api.space.rocketry.GSE;

import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.fuel.FuelTank;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.fuel.network.FuelNetwork;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.launchcontrol.profile.GSEProfile;

public class GSEComputer {

    //TODO: should be expandable.
    private FuelNetwork fuelNetwork;

    private GSEProfile gseProfile;



    public GSEComputer() {
        this.fuelNetwork = new FuelNetwork(3);
        this.gseProfile = new GSEProfile(this);
    }

    public void init() {

    }

    public FuelNetwork getFuelNetwork() {
        return fuelNetwork;
    }

    public void setFuelNetwork(FuelNetwork fuelNetwork) {
        this.fuelNetwork = fuelNetwork;
    }

    public GSEProfile getGseProfile() {
        return gseProfile;
    }

    public void setGseProfile(GSEProfile gseProfile) {
        this.gseProfile = gseProfile;
    }
}
