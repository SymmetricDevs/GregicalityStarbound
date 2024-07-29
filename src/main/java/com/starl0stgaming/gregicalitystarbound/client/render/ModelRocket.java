package com.starl0stgaming.gregicalitystarbound.client.render;

import net.minecraft.util.ResourceLocation;

import com.starl0stgaming.gregicalitystarbound.Tags;
import com.starl0stgaming.gregicalitystarbound.common.entity.EntityRocket;

import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelRocket extends AnimatedGeoModel<EntityRocket> {

    private static final ResourceLocation modelResource = new ResourceLocation(Tags.MODID, "geo/drop_pod.geo.json");
    private static final ResourceLocation textureResource = new ResourceLocation(Tags.MODID,
            "textures/entities/drop_pod.png");
    private static final ResourceLocation animationResource = new ResourceLocation(Tags.MODID,
            "animations/drop_pod.animation.json");

    @Override
    public ResourceLocation getModelLocation(EntityRocket entityDropPod) {
        return modelResource;
    }

    @Override
    public ResourceLocation getTextureLocation(EntityRocket entityDropPod) {
        return textureResource;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityRocket entityDropPod) {
        return animationResource;
    }
}
