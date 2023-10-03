package com.starl0stgaming.gregicalitystarbound.api.sound;

import com.starl0stgaming.gregicalitystarbound.GregicalityStarbound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class GCSBSounds {

    public static SoundEvent ROCKET_LAUNCH;

    public static void registerSounds(){
        ROCKET_LAUNCH = registerSound("entity.rocket_launch");
    }

    private static SoundEvent registerSound(String soundNameIn) {
        ResourceLocation location = new ResourceLocation(GregicalityStarbound.MODID, soundNameIn);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(location);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }
}
