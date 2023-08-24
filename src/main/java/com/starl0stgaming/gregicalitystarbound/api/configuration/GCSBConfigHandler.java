package com.starl0stgaming.gregicalitystarbound.api.configuration;

import net.minecraftforge.fml.common.Loader;
import sun.misc.GC;

import java.io.File;
import java.nio.file.Path;

public class GCSBConfigHandler {



    private Path configDirPath = Loader.instance().getConfigDir().toPath();

    private File GCSBConfigFolder = new File(Loader.instance().getConfigDir(), "gregicalitystarbound");


    public void init() {
        try {
            GCSBConfigFolder.mkdirs();
        } catch (Exception e) {
            throw e;
        }
    }
}
