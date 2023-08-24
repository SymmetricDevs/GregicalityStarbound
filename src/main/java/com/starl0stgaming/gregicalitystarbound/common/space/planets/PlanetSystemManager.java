package com.starl0stgaming.gregicalitystarbound.common.space.planets;

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
        Planet planet = new Planet(1, "earthtest");
        Planet planet2 = new Planet(2, "marstest");

        if(planet.load()) {
            planetList.add(planet);
        } else {
            return false;
        }

        if(planet2.load()) {
            planetList.add(planet2);
        } else {
            return false;
        }

        return false;
    }


    public List<Planet> getPlanetList() {
        return planetList;
    }
}
