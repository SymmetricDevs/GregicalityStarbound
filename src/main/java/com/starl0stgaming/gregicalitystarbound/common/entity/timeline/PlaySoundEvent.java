package com.starl0stgaming.gregicalitystarbound.common.entity.timeline;

import com.starl0stgaming.gregicalitystarbound.api.space.timeline.TimelineTask;
import com.starl0stgaming.gregicalitystarbound.common.entity.EntityRocket;

public class PlaySoundEvent implements TimelineTask<EntityRocket> {
    @Override
    public void execute(EntityRocket handle) {
        if (handle.world.isRemote) {
            handle.playRocketSound();
        }
    }
}
