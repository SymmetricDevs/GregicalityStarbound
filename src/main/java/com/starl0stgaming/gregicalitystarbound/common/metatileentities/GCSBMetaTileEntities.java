package com.starl0stgaming.gregicalitystarbound.common.metatileentities;

import static com.starl0stgaming.gregicalitystarbound.api.util.StringUtil.gcsbId;
import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;

import com.starl0stgaming.gregicalitystarbound.common.metatileentities.multi.launchpad.MetaTileEntityLaunchPad;

public class GCSBMetaTileEntities {

    public static MetaTileEntityLaunchPad LAUNCH_PAD;

    public static void init() {
        LAUNCH_PAD = registerMetaTileEntity(11000, new MetaTileEntityLaunchPad(gcsbId("launch_pad")));
    }
}
