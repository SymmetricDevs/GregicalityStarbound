package com.starl0stgaming.gregicalitystarbound.common.space;

import com.starl0stgaming.gregicalitystarbound.GregicalityStarbound;
import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.space.planets.Planet;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.launchcontrol.LaunchController;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.rocket.factory.RocketBuilder;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.rocket.factory.RocketFactory;
import com.starl0stgaming.gregicalitystarbound.api.space.solarsystem.SolarSystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SpaceController {

    public static final CelestialBodyManager CELESTIAL_BODY_MANAGER = new CelestialBodyManager();
    public static final RocketFactory ROCKET_FACTORY = new RocketFactory();

    public SpaceController() {

    }


    public void initializeSpace() {
        /*
        LOADING LOGIC

        First planets and suns are loaded, then solar systems and galaxies
         */

        CELESTIAL_BODY_MANAGER.init();

        GCSBLog.LOGGER.info("Planets successfully loaded!");

        // Load Suns


        // Solar Systems


        // Galaxies



        // Rocketry

        //simple example usage
        ROCKET_FACTORY.createRocket(1, "Eagle Fucker 3000");



    }




    public void loadSolarSystems() {

    }
}
