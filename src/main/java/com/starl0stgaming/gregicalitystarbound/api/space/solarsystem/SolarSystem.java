package com.starl0stgaming.gregicalitystarbound.api.space.solarsystem;

import com.starl0stgaming.gregicalitystarbound.api.space.Sun;
import com.starl0stgaming.gregicalitystarbound.api.space.planets.Planet;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class SolarSystem {

    private String name;
    private int id;

    private Sun sun;

    private boolean isLoaded = false;

    private List<Planet> planetList;


    public SolarSystem(String name, int id, Sun sun) {
        this.name = name;
        this.id = id;
        this.sun = sun;
    }

    public void load() {
        if(!isLoaded) {
            isLoaded = true;
            this.planetList = new ArrayList<>();
        }
    }

    public void unload() {
        if(isLoaded) {
            isLoaded = false;
        }
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sun getSun() {
        return sun;
    }

    public void setSun(Sun sun) {
        this.sun = sun;
    }

    public void addPlanet(@Nonnull Planet planet) {
        this.planetList.add(planet);
    }

    public void removePlanet(Planet planet) {
        this.planetList.remove(planet);
    }

    @Override
    public String toString() {
        return "Solar System ID: " + this.getId() + " Solar System Name: " + this.getName() + " Solar System Sun Name: " + this.getSun().getName();
    }
}
