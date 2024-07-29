package com.starl0stgaming.gregicalitystarbound.client.sound;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;

import com.starl0stgaming.gregicalitystarbound.common.entity.EntityRocket;

public class MovingSoundRocket extends MovingSound {

    private final EntityRocket entityRocket;
    private float distance = 0.0F;

    public MovingSoundRocket(EntityRocket entityRocket) {
        super(GCSBSounds.ROCKET_LAUNCH, SoundCategory.NEUTRAL);
        this.entityRocket = entityRocket;
        this.repeat = false;
        this.repeatDelay = 0;
        this.volume = 7F;
    }

    public void startPlaying() {
        this.volume = 8F;
    }

    public void stopPlaying() {
        this.volume = 0.0F;
    }

    @Override
    public void update() {
        if (this.entityRocket.isDead) {
            this.donePlaying = true;
        } else {
            this.xPosF = (float) this.entityRocket.posX;
            this.yPosF = (float) this.entityRocket.posY;
            this.zPosF = (float) this.entityRocket.posZ;

            this.distance = MathHelper.clamp(this.distance + 0.0025F, 0.0F, 10F);
        }
    }
}
