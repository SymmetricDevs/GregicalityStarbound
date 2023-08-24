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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GCSBConfigHandler {



    private File GCSBConfigFolder = new File(Loader.instance().getConfigDir(), "gregicalitystarbound");

    private File planetConfigFile = new File(GCSBConfigFolder, "planets.json");
    private File solarSystemConfigFile = new File(GCSBConfigFolder, "solarsystems.json");

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();


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


    public List<Planet> getPlanetListFromConfig() {

        try {
            Reader reader = Files.newBufferedReader(planetConfigFile.toPath());

            List<Planet> planetList = GSON.fromJson(reader, new TypeToken<List<Planet>>() {}.getType());

            planetList.forEach(System.out::println);

            reader.close();

            return planetList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
