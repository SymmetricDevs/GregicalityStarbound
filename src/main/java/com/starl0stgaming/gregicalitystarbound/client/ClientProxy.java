package com.starl0stgaming.gregicalitystarbound.client;

import com.starl0stgaming.gregicalitystarbound.client.sound.GCSBSounds;
import com.starl0stgaming.gregicalitystarbound.common.CommonProxy;
import com.starl0stgaming.gregicalitystarbound.common.entity.EntityRegistration;

public class ClientProxy extends CommonProxy {

    public void preLoad() {
        super.preLoad();
        EntityRegistration.registerRenders();
        GCSBSounds.registerSounds();
    }
}
