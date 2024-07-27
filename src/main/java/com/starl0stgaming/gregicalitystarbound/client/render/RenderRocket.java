package com.starl0stgaming.gregicalitystarbound.client.render;

import com.starl0stgaming.gregicalitystarbound.Tags;
import com.starl0stgaming.gregicalitystarbound.common.entity.EntityRocket;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@SideOnly(Side.CLIENT)
public class RenderRocket extends GeoEntityRenderer<EntityRocket> {
    public RenderRocket(RenderManager manager) {
        super(manager, new ModelRocket());
    }
}
