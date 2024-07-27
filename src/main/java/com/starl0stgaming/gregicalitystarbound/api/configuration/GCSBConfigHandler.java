package com.starl0stgaming.gregicalitystarbound.api.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.fml.common.Loader;

import java.io.File;

public class GCSBConfigHandler {


    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    private boolean loadDefaultPlanets = true;
    private File GCSBConfigFolder = new File(Loader.instance().getConfigDir(), "gregicalitystarbound");
    private File solarSystemConfigFolder = new File(GCSBConfigFolder, "solarsystems");

    public void init() {
        GCSBConfigFolder.mkdirs();


        solarSystemConfigFolder.mkdirs();


    }


    public File getGCSBConfigFolder() {
        return GCSBConfigFolder;
    }

    public File getSolarSystemConfigFolder() {
        return solarSystemConfigFolder;
    }

}
