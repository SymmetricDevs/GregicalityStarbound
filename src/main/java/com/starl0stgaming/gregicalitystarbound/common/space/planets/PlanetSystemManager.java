package com.starl0stgaming.gregicalitystarbound.common.space.planets;

import com.starl0stgaming.gregicalitystarbound.GregicalityStarbound;
import com.starl0stgaming.gregicalitystarbound.api.space.planets.Planet;

import java.util.ArrayList;
import java.util.List;

public class PlanetSystemManager {


    public List<Planet> planetList = new ArrayList<>();


    public void init() {
        if(!this.initializePlanets()) return;

        System.out.println("[GCSB] Planets loaded successfully! ");

    }

    private boolean initializePlanets() {
        //TODO: Get config file and load a planet from there.
        this.planetList = GregicalityStarbound.CONFIG_HANDLER.getPlanetListFromConfig();



        return true;
    }


    public List<Planet> getPlanetList() {
        return planetList;
    }
}
