package com.starl0stgaming.gregicalitystarbound.api.configuration;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.starl0stgaming.gregicalitystarbound.api.space.planets.Planet;
import net.minecraftforge.fml.common.Loader;
import sun.misc.GC;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GCSBConfigHandler {



    private boolean loadDefaultPlanets = true;


    private File GCSBConfigFolder = new File(Loader.instance().getConfigDir(), "gregicalitystarbound");

    private File solarSystemConfigFolder = new File(GCSBConfigFolder, "solarsystems");




    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();


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
