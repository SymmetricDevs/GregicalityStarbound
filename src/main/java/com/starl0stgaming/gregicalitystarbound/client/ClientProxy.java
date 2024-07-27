package com.starl0stgaming.gregicalitystarbound.client;

import com.starl0stgaming.gregicalitystarbound.client.sound.GCSBSounds;
import com.starl0stgaming.gregicalitystarbound.common.CommonProxy;

public class ClientProxy extends CommonProxy {
    public void preLoad() {
        super.preLoad();
        GCSBSounds.registerSounds();
    }
}
