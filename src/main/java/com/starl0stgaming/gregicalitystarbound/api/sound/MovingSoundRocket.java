package com.starl0stgaming.gregicalitystarbound.api.sound;

import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.RocketEntity;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;

public class MovingSoundRocket extends MovingSound {

    private final RocketEntity rocketEntity;
    private float distance = 0.0F;
    public MovingSoundRocket(RocketEntity rocketEntity) {
        super(GCSBSounds.ROCKET_LAUNCH, SoundCategory.NEUTRAL);
        this.rocketEntity = rocketEntity;
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
        if(this.rocketEntity.isDead) {
            this.donePlaying = true;
        } else {
            this.xPosF = (float) this.rocketEntity.posX;
            this.yPosF = (float) this.rocketEntity.posY;
            this.zPosF = (float) this.rocketEntity.posZ;

            this.distance = MathHelper.clamp(this.distance + 0.0025F, 0.0F, 10F);
        }
    }
}
