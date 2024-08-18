package com.starl0stgaming.gregicalitystarbound.api.configuration;

import java.io.File;

import net.minecraftforge.fml.common.Loader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GCSBConfigHandler {

    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    private File gcsbConfigFolder = new File(Loader.instance().getConfigDir(), "gregicalitystarbound");
    private File solarSystemConfigFolder = new File(gcsbConfigFolder, "solarsystems");

    public void init() {
        gcsbConfigFolder.mkdirs();

        solarSystemConfigFolder.mkdirs();
    }

    public File getGcsbConfigFolder() {
        return gcsbConfigFolder;
    }

    public File getSolarSystemConfigFolder() {
        return solarSystemConfigFolder;
    }
}
