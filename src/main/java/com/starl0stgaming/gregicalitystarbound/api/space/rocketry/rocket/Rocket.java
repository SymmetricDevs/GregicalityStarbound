package com.starl0stgaming.gregicalitystarbound.api.space.rocketry.rocket;

import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.fuel.FuelTank;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.fuel.network.FuelNetwork;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.payload.PayloadInfo;

public class Rocket {

    //TODO: unite with RocketEntity class

    //fuel
    private FuelNetwork fuelNetwork;

    private FuelTank loxTank1;
    private FuelTank fuelTank1;

    //engines


    //structure


    //logic
    private int id;
    private String name;

    private PayloadInfo payloadInfo;

    //api




    public Rocket(int id, String name) {
        this.fuelNetwork = new FuelNetwork(0);

        this.loxTank1 = new FuelTank(1000000, 100000); //pressure is WIP
        this.fuelTank1 = new FuelTank(800000, 100000);

        this.id = id;
        this.name = name;

    }

    public void init() {
        this.fuelNetwork.addFuelTank(this.loxTank1);
        this.fuelNetwork.addFuelTank(this.fuelTank1);


    }

    public FuelNetwork getFuelNetwork() {
        return fuelNetwork;
    }

    public void setFuelNetwork(FuelNetwork fuelNetwork) {
        this.fuelNetwork = fuelNetwork;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
