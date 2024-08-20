package com.starl0stgaming.gregicalitystarbound.common.entity.timeline;

import com.starl0stgaming.gregicalitystarbound.api.space.timeline.TimelineTask;
import com.starl0stgaming.gregicalitystarbound.common.entity.EntityRocket;

public class ParticlesEvent implements TimelineTask<EntityRocket> {
    private boolean turnOn;

    public ParticlesEvent(boolean turnOn) {
        this.turnOn = turnOn;
    }

    @Override
    public void execute(EntityRocket handle) {
        if (handle.world.isRemote) {
            handle.particles = turnOn;
        }
    }
}
