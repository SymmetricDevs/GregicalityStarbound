package com.starl0stgaming.gregicalitystarbound.api.space.rocketry.guidance;

import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.RocketEntity;
import crazypants.enderio.base.config.recipes.xml.Fuel;

import java.util.List;

public class GuidanceComputer {
    private RocketEntity rocketEntity;

    private boolean isLaunched;
    private boolean isCountdownStarted;
    private float startHeight;
    private int age;
    private int launchTime;
    private int flightTime;

    public int countdownTimerSeconds;


    //Calculates position, acceleration, trajectory, orbit and handles staging, fuel tanks and payload alongside propulsion

    public GuidanceComputer(RocketEntity rocketEntity) {
        this.rocketEntity = rocketEntity;
    }

    public void init() {

    }

    public void startCountdown() {
        this.setCountdownStarted(true);
        this.setLaunchTime(12000);
        GCSBLog.LOGGER.info("[GuidanceComputer] T-10 minutes");

        GCSBLog.LOGGER.info("[GuidanceComputer] Initiating Launch Procedures for rocket with ID " + this.rocketEntity.getEntityId());

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
}
