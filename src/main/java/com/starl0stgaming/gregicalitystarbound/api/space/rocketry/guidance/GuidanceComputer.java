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

    private boolean isAbleToLaunch;


    //Calculates position, acceleration, trajectory, orbit and handles staging, fuel tanks and payload alongside propulsion

    public GuidanceComputer(RocketEntity rocketEntity) {
        this.rocketEntity = rocketEntity;
    }

    public void init() {
        this.gseComputer = new GSEComputer();
        //TODO: should be built from GSE's connected piping and tanks;
        this.gseComputer.setFuelFlowRate(500);


        this.fuelNetwork = new FuelNetwork(1);

        FuelTank fuelTank1 = new FuelTank(1000000, 10000);
        FuelTank fuelTank2 = new FuelTank(1000000, 10000);

        this.fuelNetwork.addFuelTank(fuelTank1);
        this.fuelNetwork.addFuelTank(fuelTank2);

        //TODO: BUILD ROCKET STATS: DELTA-V, MASS AND CARGO CAPACITY
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

    public void startCountdown() {
       if(this.missionConfig != null) {

           GCSBLog.LOGGER.info("[GuidanceComputer] Executing Pre-Countdown Mission Checks...");
           boolean passedPreCountdownChecks;

           boolean enoughDeltaV;
           if (this.maximumDeltaV < this.missionConfig.getTrajectory().getRequiredDeltaV()) {
               GCSBLog.LOGGER.warn("[GuidanceComputer] Cannot continue with launch procedures: Not enough deltaV capacity for required mission ");
               enoughDeltaV = false;
           } else {
               enoughDeltaV = true;
               GCSBLog.LOGGER.info("[GuidanceComputer] Required deltaV: " + this.missionConfig.getTrajectory().getRequiredDeltaV() + " [PASSED] ");
           }
           //TODO: Change to "GSE authorizes launch", which are checks by the GSE to keep it more organized
           boolean enoughFuelFillRate;

           if (this.gseComputer.getFuelFlowRate() >= this.missionConfig.getRequiredFuelRate()) {
               enoughFuelFillRate = true;
               GCSBLog.LOGGER.info("[GuidanceComputer] Required fuel flow rate to comply with launch time requirements: " + this.missionConfig.getRequiredFuelRate() + "L/t [PASSED]");
           } else {
               enoughFuelFillRate = false;
           }

           GCSBLog.LOGGER.info("[GuidanceComputer] Required fuel flow rate to comply with launch time requirements: " + this.missionConfig.getRequiredFuelRate() + "L/t [FAILED]");

           //TODO: Check for max cargo capacity to chosen orbit
           boolean canCarryCargo;

           int payloadMass = this.getMissionConfig().getPayloadInfo().getMass();

           this.massTotal = this.massPropellant + this.massStructural + payloadMass;

           //TODO: change Ve (exhaust velocity) to be dynamic
           double maxCargoMassToOrbit = (this.massPropellant / (1 - Math.pow(2.718281828459045, (this.getMissionConfig().getTrajectory().getRequiredDeltaV() / 250))) - this.massStructural);

           if (this.maximumCargoCapacity > maxCargoMassToOrbit) {
               canCarryCargo = true;
               GCSBLog.LOGGER.info("[GuidanceComputer] Enough cargo capacity: " + maxCargoMassToOrbit + "t [PASSED]");
           } else {
               canCarryCargo = false;
           }

           if (enoughDeltaV && enoughFuelFillRate && canCarryCargo) {
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

       }

    }

    public void onUpdate() {

        if(isCountdownStarted() && this.fuelNetwork.getCurrentStoredFuel() != 2000000) {
            this.fuelNetwork.addFuelToNetwork(this.getMissionConfig().getRequiredFuelRate());
        }

        if(this.fuelNetwork.getCurrentStoredFuel() == 2000000 && isCountdownStarted()) {
            GCSBLog.LOGGER.info("[GuidanceComputer] Fuel Loading Complete: " + this.fuelNetwork.getCurrentStoredFuel() + "L/s");
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
}
