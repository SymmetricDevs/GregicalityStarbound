package com.starl0stgaming.gregicalitystarbound.api.configuration;

import net.minecraftforge.fml.common.Loader;
import sun.misc.GC;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class GCSBConfigHandler {



    private File GCSBConfigFolder = new File(Loader.instance().getConfigDir(), "gregicalitystarbound");

    private File planetConfigFile = new File(GCSBConfigFolder, "planets.json");
    private File solarSystemConfigFile = new File(GCSBConfigFolder, "solarsystems.json");


    public void init() {
        try {
            GCSBConfigFolder.mkdirs();

            this.initializeConfigFiles();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void initializeConfigFiles() throws IOException {
        try {
            planetConfigFile.createNewFile();
            solarSystemConfigFile.createNewFile();
        } catch (IOException e) {
            throw e;
        }
    }


}
