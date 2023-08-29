package com.starl0stgaming.gregicalitystarbound.api.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.starl0stgaming.gregicalitystarbound.api.space.planets.Planet;
import net.minecraftforge.fml.common.Loader;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GCSBConfigHandler {



    private boolean loadDefaultPlanets = true;


    private File GCSBConfigFolder = new File(Loader.instance().getConfigDir(), "gregicalitystarbound");

    private File planetConfigFolder = new File(GCSBConfigFolder, "planets");

    private File planetConfigFile = new File(GCSBConfigFolder, "planets.json");
    private File solarSystemConfigFile = new File(GCSBConfigFolder, "solarsystems.json");

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();


    public void init() {
        try {
            GCSBConfigFolder.mkdirs();

            planetConfigFolder.mkdirs();

            this.initializeConfigFiles();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void initializeConfigFiles() throws IOException {
        try {
            solarSystemConfigFile.createNewFile();
        } catch (IOException e) {
            throw e;
        }
    }


    public List<Planet> getPlanetListFromFolder() {
        List<Planet> planetList = new ArrayList<>();

        try  {
            Stream<Path> stream = Files.walk(planetConfigFolder.toPath());

            stream.filter(Files::isRegularFile).forEach((planetPath) -> {
                try {
                    Reader reader = Files.newBufferedReader(planetPath);

                    Planet planet = GSON.fromJson(reader, Planet.class);
                    planetList.add(planet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            planetList.forEach(System.out::println);
            return planetList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
