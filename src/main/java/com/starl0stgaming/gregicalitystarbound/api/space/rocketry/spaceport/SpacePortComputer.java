package com.starl0stgaming.gregicalitystarbound.api.space.rocketry.spaceport;

import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.GSE.GSEComputer;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.guidance.GuidanceComputer;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.mission.MissionConfig;

import java.util.ArrayList;
import java.util.List;

public class SpacePortComputer {

    private GSEComputer gseComputer;

    //TODO: change once rocket framework is refactored
    private List<GuidanceComputer> rockets;



    public SpacePortComputer() {
        this.gseComputer = new GSEComputer();
        this.rockets = new ArrayList<>();



        this.init();
    }

    public void init() {


    }

    public boolean approveLaunch(GuidanceComputer guidanceComputer) {
        boolean passedMissionConfigChecks = executeMissionConfigChecks(guidanceComputer.getMissionConfig(), guidanceComputer);
    }

    public boolean executeMissionConfigChecks(MissionConfig missionConfig, GuidanceComputer guidanceComputer) {
        if(missionConfig != null) {
            GCSBLog.LOGGER.info("[GuidanceComputer] Executing Pre-Countdown Mission Checks...");
            boolean passedPreCountdownChecks;

            boolean enoughDeltaV;
            if (guidanceComputer.getMaximumDeltaV() < guidanceComputer.getMissionConfig().getTrajectory().getRequiredDeltaV()) {
                GCSBLog.LOGGER.warn("[GuidanceComputer] Cannot continue with launch procedures: Not enough deltaV capacity for required mission ");
                enoughDeltaV = false;
            } else {
                enoughDeltaV = true;
                GCSBLog.LOGGER.info("[GuidanceComputer] Required deltaV: " + guidanceComputer.getMissionConfig().getTrajectory().getRequiredDeltaV() + " [PASSED] ");
            }
            //TODO: Change to "GSE authorizes launch", which are checks by the GSE to keep it more organized
            boolean enoughFuelFillRate;

            if (this.gseComputer.getFuelFlowRate() >= guidanceComputer.getMissionConfig().getRequiredFuelRate()) {
                enoughFuelFillRate = true;
                GCSBLog.LOGGER.info("[GuidanceComputer] Required fuel flow rate to comply with launch time requirements: " + this.missionConfig.getRequiredFuelRate() + "L/t [PASSED]");
            } else {
                enoughFuelFillRate = false;
                GCSBLog.LOGGER.info("[GuidanceComputer] Required fuel flow rate to comply with launch time requirements: " + this.missionConfig.getRequiredFuelRate() + "L/t [FAILED]");
            }



            //TODO: Check for max cargo capacity to chosen orbit
            boolean canCarryCargo;

            int payloadMass = guidanceComputer.getMissionConfig().getPayloadInfo().getMass();

            guidanceComputer.getMassTotal() = this.massPropellant + this.massStructural + payloadMass;

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
        }

        return false;
    }
}
