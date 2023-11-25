package com.starl0stgaming.gregicalitystarbound.api.space.rocketry.mission;

import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.fuel.network.FuelNetwork;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.payload.PayloadInfo;

import java.util.List;

public class MissionConfig {


    private int launchTime;
    private Trajectory trajectory;

    //private Timestamps timestamps
    private PayloadInfo payloadInfo;

    private int requiredFuelRate;


    public MissionConfig() {

    }

    public void build(FuelNetwork fuelNetwork) {
        System.out.println(this.getLaunchTime());

        //get required mission deltaV
        //TODO: Change to get planet id from destination orbit and get the planet and its mass
        double planetaryConstant = (6.67e-11) * (5.97e24);

        //TODO: change to get from planet
        double planetRadius = 6.378e+6;


        this.getTrajectory().setRequiredDeltaV( Math.sqrt((planetaryConstant) / (planetRadius + this.getTrajectory().getDestinationOrbit().getOrbitalHeight())));

        //calculate the fuel flow rate needed to be able to comply with launchTime
        //TODO: set to change depending on rocket's fuel tanks
        this.setRequiredFuelRate(fuelNetwork.getMaximumFuelCapacity() / this.getLaunchTime());

    }

    public int getLaunchTime() {
        return launchTime;
    }

    public void setLaunchTime(int launchTime) {
        this.launchTime = launchTime;
    }

    public Trajectory getTrajectory() {
        return trajectory;
    }

    public void setTrajectory(Trajectory trajectory) {
        this.trajectory = trajectory;
    }

    public int getRequiredFuelRate() {
        return requiredFuelRate;
    }

    public void setRequiredFuelRate(int requiredFuelRate) {
        this.requiredFuelRate = requiredFuelRate;
    }

    public PayloadInfo getPayloadInfo() {
        return payloadInfo;
    }

    public void setPayloadInfo(PayloadInfo payloadInfo) {
        this.payloadInfo = payloadInfo;
    }

    @Override
    public String toString() {
        return "Launch Time: " + this.getLaunchTime() + " Fuel Fill Rate: " + this.getRequiredFuelRate() + " hi I exist (in theory)";
    }
}
