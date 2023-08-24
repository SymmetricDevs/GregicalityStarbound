package com.starl0stgaming.gregicalitystarbound.common.space;

import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.common.space.planets.PlanetSystemManager;

public class SpaceController {

    private static final PlanetSystemManager PLANET_MANAGER = new PlanetSystemManager();



    public SpaceController() {

    }


    public void initializeSpace() {
        /*
        LOADING LOGIC

        First planets and suns are loaded, then solar systems and galaxies
         */

        PLANET_MANAGER.init();
        if(!PLANET_MANAGER.isLoaded()) {
            return;
        }
        GCSBLog.LOGGER.info("Planets successfully loaded!");

        // Load Suns


        // Solar Systems


        // Galaxies

    }
}
