package com.starl0stgaming.gregicalitystarbound.api.space.rocketry.guidance;

import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.RocketEntity;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.mission.MissionConfig;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.mission.Trajectory;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.orbit.Orbit;
import crazypants.enderio.base.config.recipes.xml.Fuel;

import java.util.List;

public class GuidanceComputer {
    private RocketEntity rocketEntity;

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

    private boolean isAbleToLaunch;


    //Calculates position, acceleration, trajectory, orbit and handles staging, fuel tanks and payload alongside propulsion

    public GuidanceComputer(RocketEntity rocketEntity) {
        this.rocketEntity = rocketEntity;
    }

    public void init() {
        //TODO: BUILD ROCKET STATS: DELTA-V, MASS AND CARGO CAPACITY
        this.maximumDeltaV = 10000;


        Orbit orbit = new Orbit();
        orbit.setOrbitalHeight(450000);
        orbit.setPlanetID(5);

        Trajectory trajectory = new Trajectory(orbit);

        MissionConfig missionConfig = new MissionConfig();
        missionConfig.setLaunchTime(12000);
        missionConfig.setTrajectory(trajectory);
        missionConfig.build();

        this.setMissionConfig(missionConfig);
    }

    public void startCountdown() {
       if(this.missionConfig != null) {

           GCSBLog.LOGGER.info("[GuidanceComputer] Executing Pre-Countdown Mission Checks...");
           boolean passedPreCountdownChecks;

           boolean enoughDeltaV;
           if(this.maximumDeltaV < this.missionConfig.getTrajectory().getRequiredDeltaV()) {
               GCSBLog.LOGGER.warn("[GuidanceComputer] Cannot continue with launch procedures: Not enough deltaV capacity for required mission ");
               enoughDeltaV = false;
           } else {
               enoughDeltaV = true;
               GCSBLog.LOGGER.info("[GuidanceComputer] Required deltaV: " + this.missionConfig.getTrajectory().getRequiredDeltaV() + " [PASSED] ");
           }
           //TODO: check if GSE can provide enough fuel fill rate
           boolean enoughFuelFillRate = true;
           GCSBLog.LOGGER.info("[GuidanceComputer] Required fuel flow rate to comply with launch time requirements: " + this.missionConfig.getRequiredFuelRate() + "L/t [PASSED]");

           //TODO: Check for max cargo capacity to chosen orbit
           boolean canCarryCargo = true;
           GCSBLog.LOGGER.info("[GuidanceComputer] Enough cargo capacity: " + 5.6 + "t [PASSED]");

           if(enoughDeltaV && enoughFuelFillRate && canCarryCargo) {
               passedPreCountdownChecks = true;
           } else {

               passedPreCountdownChecks = false;
           }

           if (!passedPreCountdownChecks) {
               //TODO: specify what is missing
               GCSBLog.LOGGER.warn("[GuidanceComputer] Pre-Countdown Checks failed, aborting.");
               return;
           }

           this.setLaunchTime(this.missionConfig.getLaunchTime());
           this.setCountdownStarted(true);

           GCSBLog.LOGGER.info("[GuidanceComputer] Initiating Launch Procedures for rocket with entity id " + this.rocketEntity.getEntityId());
           GCSBLog.LOGGER.info("[GuidanceComputer] T-10 minutes");

       } else {
           GCSBLog.LOGGER.warn("[Guidance Computer] Can't initiate Pre-Launch Checks: No mission config loaded");
       }

    }

    public void onUpdate() {
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
}
