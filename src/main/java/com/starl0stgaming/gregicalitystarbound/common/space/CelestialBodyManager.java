package com.starl0stgaming.gregicalitystarbound.common.space;

import java.util.ArrayList;
import java.util.List;

import com.starl0stgaming.gregicalitystarbound.GregicalityStarbound;
import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.space.solarsystem.SolarSystem;

public class CelestialBodyManager {

    public List<SolarSystem> solarSystems = new ArrayList<>();

    public CelestialBodyManager() {}

    public void init() {
        GregicalityStarbound.SPACE_CONFIG_HANDLER.getSolarSystemsFolder().forEach((folder) -> {
            GCSBLog.LOGGER.info(folder.getPath());
            SolarSystem solarSystem = GregicalityStarbound.SPACE_CONFIG_HANDLER.createSolarSystemFromFolder(folder);
            this.solarSystems.add(solarSystem);
            GCSBLog.LOGGER
                    .info("Loaded Solar System with ID " + solarSystem.getId() + " and Name: " + solarSystem.getName());
        });
    }
}
