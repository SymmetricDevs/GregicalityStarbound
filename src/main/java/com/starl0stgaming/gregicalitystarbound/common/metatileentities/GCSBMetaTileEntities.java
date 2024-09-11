package com.starl0stgaming.gregicalitystarbound.common.metatileentities;

import static com.starl0stgaming.gregicalitystarbound.api.util.StringUtil.gcsbId;
import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;

import com.starl0stgaming.gregicalitystarbound.client.render.textures.GCSBTextures;
import com.starl0stgaming.gregicalitystarbound.common.metatileentities.multi.launchpad.MetaTileEntityLaunchPad;
import com.starl0stgaming.gregicalitystarbound.common.metatileentities.multi.rocketcore.MetaTileEntityRocket;

public class GCSBMetaTileEntities {

    public static MetaTileEntityLaunchPad LAUNCH_PAD;
    public static MetaTileEntityRocket ROCKET_CORE;

    public static void init() {
        LAUNCH_PAD = registerMetaTileEntity(11000, new MetaTileEntityLaunchPad(gcsbId("launch_pad")));
        ROCKET_CORE = registerMetaTileEntity(11001, new MetaTileEntityRocket(gcsbId("rocket_core")));
    }
}
