package com.starl0stgaming.gregicalitystarbound.api.space.rocketry.guidance;

import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.GSE.GSEComputer;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.RocketEntity;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.fuel.FuelTank;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.fuel.network.FuelNetwork;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.mission.MissionConfig;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.mission.Trajectory;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.orbit.Orbit;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.payload.PayloadInfo;
import crazypants.enderio.base.config.recipes.xml.Fuel;

import java.util.List;

public class GuidanceComputer {
    private RocketEntity rocketEntity;

    private GSEComputer gseComputer;

    private FuelNetwork fuelNetwork;

    private MissionConfig missionConfig;

    private boolean isLaunched;
    private boolean isCountdownStarted;
    private float startHeight;
    private int age;
    private int launchTime;
    private int flightTime;

    public int countdownTimerSeconds;

    //rocket stats
    private int maximumDeltaV;
    private int maximumCargoCapacity;

    private int massTotal;
    private int massStructural;
    private int massPropellant;

    private boolean isLaunchApproved;


    //Calculates position, acceleration, trajectory, orbit and handles staging, fuel tanks and payload alongside propulsion

    public GuidanceComputer(RocketEntity rocketEntity) {
        this.rocketEntity = rocketEntity;
    }

    public void init() {
        this.fuelNetwork = new FuelNetwork(1);

        FuelTank fuelTank1 = new FuelTank(1000000, 10000);
        FuelTank fuelTank2 = new FuelTank(1000000, 10000);

        this.fuelNetwork.addFuelTank(fuelTank1);
        this.fuelNetwork.addFuelTank(fuelTank2);

        //TODO: Need to build deltaV, mass values (propellant, structural, total), cargo capacity, Mission Config (Orbit, PayloadInfo, trajectory), fuel network based off parts.
        this.maximumDeltaV = 10000;

        this.massPropellant = 500;
        this.massStructural = 300;

        this.maximumCargoCapacity = 1000;

        Orbit orbit = new Orbit();
        orbit.setOrbitalHeight(450000);
        orbit.setPlanetID(5);

        PayloadInfo payloadInfo = new PayloadInfo(400);

        Trajectory trajectory = new Trajectory(orbit);

        MissionConfig missionConfig = new MissionConfig();
        missionConfig.setLaunchTime(12000);
        missionConfig.setTrajectory(trajectory);
        missionConfig.setPayloadInfo(payloadInfo);
        missionConfig.build(this.fuelNetwork);

        this.setMissionConfig(missionConfig);
    }

    public void buildValues() {

    }

    public void startCountdown() {



    }

    public void onUpdate() {

        if(isCountdownStarted() && this.fuelNetwork.getCurrentStoredFuel() != 2000000) {
            this.fuelNetwork.addFuelToNetwork(this.getMissionConfig().getRequiredFuelRate());
        }

        if(this.fuelNetwork.getCurrentStoredFuel() == 2000000 && isCountdownStarted()) {
            GCSBLog.LOGGER.info("[GuidanceComputer] Fuel Loading Complete: " + this.fuelNetwork.getCurrentStoredFuel() + "L/s");
        }

        if(this.isCountdownStarted() && this.countdownTimerSeconds % 60 == 0) {
            GCSBLog.LOGGER.info("[GuidanceComputer] Current stored fuel: " + this.fuelNetwork.getCurrentStoredFuel());
        }

        if (this.isCountdownStarted() && !isLaunched() && this.rocketEntity.getAge() >= this.getLaunchTime()) {
            this.launch();
        }


        if(this.isLaunched) {
            this.setFlightTime(this.getFlightTime() + 1);

        }


    }

    public void launch() {
        this.setLaunched(true);
        this.setCountdownStarted(false);
        this.rocketEntity.onLaunch();
    }

    public boolean isLaunched() {
        return isLaunched;
    }

    public void setLaunched(boolean launched) {
        isLaunched = launched;
    }

    public boolean isCountdownStarted() {
        return isCountdownStarted;
    }

    public void setCountdownStarted(boolean countdownStarted) {
        isCountdownStarted = countdownStarted;
    }

    public float getStartHeight() {
        return startHeight;
    }

    public void setStartHeight(float startHeight) {
        this.startHeight = startHeight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getLaunchTime() {
        return launchTime;
    }

    public void setLaunchTime(int launchTime) {
        this.launchTime = launchTime;
    }

    public int getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(int flightTime) {
        this.flightTime = flightTime;
    }

    public MissionConfig getMissionConfig() {
        return missionConfig;
    }

    public void setMissionConfig(MissionConfig missionConfig) {
        this.missionConfig = missionConfig;
    }

    public RocketEntity getRocketEntity() {
        return rocketEntity;
    }

    public void setRocketEntity(RocketEntity rocketEntity) {
        this.rocketEntity = rocketEntity;
    }

    public GSEComputer getGseComputer() {
        return gseComputer;
    }

    public void setGseComputer(GSEComputer gseComputer) {
        this.gseComputer = gseComputer;
    }

    public FuelNetwork getFuelNetwork() {
        return fuelNetwork;
    }

    public void setFuelNetwork(FuelNetwork fuelNetwork) {
        this.fuelNetwork = fuelNetwork;
    }

    public int getCountdownTimerSeconds() {
        return countdownTimerSeconds;
    }

    public void setCountdownTimerSeconds(int countdownTimerSeconds) {
        this.countdownTimerSeconds = countdownTimerSeconds;
    }

    public int getMaximumDeltaV() {
        return maximumDeltaV;
    }

    public void setMaximumDeltaV(int maximumDeltaV) {
        this.maximumDeltaV = maximumDeltaV;
    }

    public int getMaximumCargoCapacity() {
        return maximumCargoCapacity;
    }

    public void setMaximumCargoCapacity(int maximumCargoCapacity) {
        this.maximumCargoCapacity = maximumCargoCapacity;
    }

    public int getMassTotal() {
        return massTotal;
    }

    public void setMassTotal(int massTotal) {
        this.massTotal = massTotal;
    }

    public int getMassStructural() {
        return massStructural;
    }

    public void setMassStructural(int massStructural) {
        this.massStructural = massStructural;
    }

    public int getMassPropellant() {
        return massPropellant;
    }

    public void setMassPropellant(int massPropellant) {
        this.massPropellant = massPropellant;
    }

    public boolean isLaunchApproved() {
        return isLaunchApproved;
    }

    public void setLaunchApproved(boolean launchApproved) {
        isLaunchApproved = launchApproved;
    }
}
