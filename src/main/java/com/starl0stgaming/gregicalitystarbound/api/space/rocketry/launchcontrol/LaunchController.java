package com.starl0stgaming.gregicalitystarbound.api.space.rocketry.launchcontrol;

import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.GSE.GSEComputer;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.guidance.GuidanceComputer;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.launchcontrol.profile.GSEProfile;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.launchcontrol.profile.LaunchProfile;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.mission.MissionConfig;

import java.util.ArrayList;
import java.util.List;

public class LaunchController {




    public LaunchController() {

    }

    public void init() {
        //idk do smth

    }

    public boolean approveLaunch(LaunchProfile launchProfile, GSEProfile gseProfile) {
        boolean passedMissionConfigChecks = executeMissionConfigChecks(launchProfile, gseProfile);
    }

    public boolean executeMissionConfigChecks(LaunchProfile launchProfile, GSEProfile gseProfile) {

        return false;
    }
}
