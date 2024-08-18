package com.starl0stgaming.gregicalitystarbound.common.space;

import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.configuration.GCSBForgeConfig;
import com.starl0stgaming.gregicalitystarbound.api.space.dimensions.space.SpaceWorldProvider;

public class SpaceController {

    public static final CelestialBodyManager CELESTIAL_BODY_MANAGER = new CelestialBodyManager();

    public SpaceController() {}

    public void initializeSpace() {
        /*
         * LOADING LOGIC
         * 
         * First space, planets and suns are loaded, then solar systems and galaxies
         */

        SpaceWorldProvider.createSpace(GCSBForgeConfig.spaceDimensionID);

        CELESTIAL_BODY_MANAGER.init();

        GCSBLog.LOGGER.info("Planets successfully loaded!");

        // Load Suns

        // Solar Systems

        // Galaxies

        // Rocketry

        // simple example usage
    }

    public void loadSolarSystems() {}
}
