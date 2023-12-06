package com.starl0stgaming.gregicalitystarbound.api.space.rocketry.launchcontrol.profile;

import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.guidance.GuidanceComputer;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.orbit.Trajectory;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.payload.PayloadInfo;

public class LaunchProfile {

    private PayloadInfo payloadInfo;
    private Trajectory trajectory;
    private GuidanceComputer guidanceComputer;


    public LaunchProfile(GuidanceComputer guidanceComputer) {
        this.guidanceComputer = guidanceComputer;
    }


    public void checkParameters() {

    }

    public PayloadInfo getPayloadInfo() {
        return payloadInfo;
    }

    public void setPayloadInfo(PayloadInfo payloadInfo) {
        this.payloadInfo = payloadInfo;
    }

    public Trajectory getTrajectory() {
        return trajectory;
    }

    public void setTrajectory(Trajectory trajectory) {
        this.trajectory = trajectory;
    }

    public GuidanceComputer getGuidanceComputer() {
        return guidanceComputer;
    }

    public void setGuidanceComputer(GuidanceComputer guidanceComputer) {
        this.guidanceComputer = guidanceComputer;
    }
}
