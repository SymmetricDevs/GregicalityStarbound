package com.starl0stgaming.gregicalitystarbound.client.render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.starl0stgaming.gregicalitystarbound.common.entity.EntityRocket;

import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@SideOnly(Side.CLIENT)
public class RenderRocket extends GeoEntityRenderer<EntityRocket> {

    public RenderRocket(RenderManager manager) {
        super(manager, new ModelRocket());
    }
}
