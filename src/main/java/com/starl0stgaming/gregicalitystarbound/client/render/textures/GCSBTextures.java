package com.starl0stgaming.gregicalitystarbound.client.render.textures;

import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GCSBTextures {
    public static final OrientedOverlayRenderer ROCKET_BASE_OVERLAY;
    static {
       ROCKET_BASE_OVERLAY = new OrientedOverlayRenderer(
                "machines/rocket_base");
    }
}
