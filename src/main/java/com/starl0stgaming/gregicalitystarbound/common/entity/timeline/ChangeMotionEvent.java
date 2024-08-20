package com.starl0stgaming.gregicalitystarbound.common.entity.timeline;

import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.space.timeline.TimelineTask;
import com.starl0stgaming.gregicalitystarbound.common.entity.EntityRocket;

public class ChangeMotionEvent implements TimelineTask<EntityRocket> {
    private double newMotionY;
    private double newAccelerationY;

    public ChangeMotionEvent(double newMotionY, double newAccelerationY) {
        this.newMotionY = newMotionY;
        this.newAccelerationY = newAccelerationY;
    }

    @Override
    public void execute(EntityRocket handle) {
        if (!Double.isNaN(newMotionY)) {
            handle.realMotionY = newMotionY;
        }
        if (!Double.isNaN(newAccelerationY)) {
            handle.accelerationY = newAccelerationY;
        }
    }
}
