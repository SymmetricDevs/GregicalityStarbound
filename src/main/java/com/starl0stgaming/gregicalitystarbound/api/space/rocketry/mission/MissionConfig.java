package com.starl0stgaming.gregicalitystarbound.api.space.rocketry.mission;

public class MissionConfig {


    private int launchTime;
    private Trajectory trajectory;

    //private Timestamps timestamps
    //private PayloadInfo payloadInfo;

    private int requiredFuelRate;

    public MissionConfig() {

    }

    public void build() {
        System.out.println(this.getLaunchTime());

        //get required mission deltaV
        //TODO: Change to get planet id from destination orbit and get the planet and its mass
        double planetaryConstant = (6.67e-11) * (5.54e24);

        //TODO: change to get from planet
        double planetRadius = 6317000;


        this.getTrajectory().setRequiredDeltaV( Math.sqrt((planetaryConstant) / (planetRadius + this.getTrajectory().getDestinationOrbit().getOrbitalHeight())));

        //calculate the fuel flow rate needed to be able to comply with launchTime
        //TODO: set to change depending on rocket's fuel tanks
        this.setRequiredFuelRate(1000000 / this.getLaunchTime());
        System.out.println(1000000 / this.getLaunchTime());

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
}
