package com.starl0stgaming.gregicalitystarbound.api.configuration.space;

import com.starl0stgaming.gregicalitystarbound.GregicalityStarbound;
import com.starl0stgaming.gregicalitystarbound.api.configuration.GCSBConfigHandler;
import com.starl0stgaming.gregicalitystarbound.api.space.planets.Planet;
import com.starl0stgaming.gregicalitystarbound.api.space.solarsystem.SolarSystem;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SpaceConfigHandler {


    public File getPlanetDirInSolarSystem(File solarSystemFolder) {
        try {
            Stream<Path> solarSystemFiles = Files.walk(solarSystemFolder.toPath());

            Path planetDirPath = solarSystemFiles.filter(Files::isDirectory).filter(entry -> entry.getFileName().toString().equals("planets")).findFirst().orElse(null);
            if (planetDirPath == null) return null;

            return planetDirPath.toFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SolarSystem createSolarSystemFromFolder(File solarSystemFolder) {
        try {
            //Solar System
            List<Path> solarSystemFiles = Files.list(solarSystemFolder.toPath())
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());

            Path solarSystemMainFilePath = solarSystemFiles.stream().findFirst().orElse(null);
            if (solarSystemMainFilePath == null) return null;
            File solarSystemFile = solarSystemMainFilePath.toFile();

            SolarSystem solarSystem = this.getSolarSystemFromFile(solarSystemFile);
            if (solarSystem == null) return null;
            solarSystem.load();

            //Planet Loading
            File planetDir = this.getPlanetDirInSolarSystem(solarSystemFolder);

            List<Planet> planetList = this.getPlanetListFromFolder(planetDir);
            if (planetList == null) return null;

            planetList.forEach((planet) -> {
                planet.load();
                solarSystem.addPlanet(planet);
            });

            return solarSystem;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    Returns a list of the folders of all the solar systems
     */
    public List<File> getSolarSystemsFolder() {
        List<File> solarSystemFolderList = new ArrayList<>();

        try {
            Path parentDirectory = GregicalityStarbound.CONFIG_HANDLER.getSolarSystemConfigFolder().toPath();

            try (Stream<Path> stream = Files.list(parentDirectory)) {
                stream.filter(Files::isDirectory)
                        .filter(entry -> !entry.getFileName().equals(parentDirectory.getFileName()))
                        .forEach((solarSystemFolderPath) -> {
                            File folder = solarSystemFolderPath.toFile();
                            solarSystemFolderList.add(folder);
                        });
            }

            return solarSystemFolderList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SolarSystem getSolarSystemFromFile(File solarSystemFile) {
        try {
            Reader reader = Files.newBufferedReader(solarSystemFile.toPath());

            SolarSystem solarSystem = GCSBConfigHandler.GSON.fromJson(reader, SolarSystem.class);

            if (solarSystem == null) return null;

            return solarSystem;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Planet> getPlanetListFromFolder(File planetFolder) {
        List<Planet> planetList = new ArrayList<>();

        try {
            Stream<Path> stream = Files.list(planetFolder.toPath());

            stream.filter(Files::isRegularFile).forEach((planetPath) -> {
                try {
                    Reader reader = Files.newBufferedReader(planetPath);

                    Planet planet = GCSBConfigHandler.GSON.fromJson(reader, Planet.class);
                    planetList.add(planet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            return planetList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
