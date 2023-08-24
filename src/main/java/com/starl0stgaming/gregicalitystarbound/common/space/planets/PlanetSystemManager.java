package com.starl0stgaming.gregicalitystarbound.common.space.planets;

import com.starl0stgaming.gregicalitystarbound.GregicalityStarbound;
import com.starl0stgaming.gregicalitystarbound.api.space.planets.Planet;

import java.util.ArrayList;
import java.util.List;

public class PlanetSystemManager {


    private boolean isLoaded;

    public List<Planet> planetList = new ArrayList<>();


    public void init() {
        if(!this.initializePlanets()) return;
        this.setLoaded(true);
    }

    private boolean initializePlanets() {
        //TODO: make actual use of the boolean return lol
        this.planetList = GregicalityStarbound.CONFIG_HANDLER.getPlanetListFromConfig();



        return true;
    }


    public List<Planet> getPlanetList() {
        return planetList;
    }


    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }
}
