package com.starl0stgaming.gregicalitystarbound.api.space.planets;

import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;

public class Planet {

    //Defining variables
    private String planetName;
    private int id;
    private int dimID;


    private boolean isLoaded;






    //Atmosphere

    public Planet(int id, String planetName) {
        this.id = id;
        this.planetName = planetName;
    }

    public Planet(int id, int dimID, String planetName) {
        this.id = id;
        this.dimID = dimID;
        this.planetName = planetName;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void load() {
        if(!isLoaded) {
            isLoaded = true;
            GCSBLog.LOGGER.info("Loaded Planet with ID " + this.getId() + " and name " + this.getPlanetName());
        }
    }

    public void unload() {
        if(isLoaded) {
            isLoaded = false;
            GCSBLog.LOGGER.info("Unloaded Planet with ID " + this.getId() + " and name " + this.getPlanetName());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDimID() {
        return dimID;
    }

    public void setDimID(int dimID) {
        this.dimID = dimID;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }


    @Override
    public String toString() {
        return "Planet Name: " + this.getPlanetName() + " Planet Id: " + this.getId() + " Planet DIM ID: " + this.getDimID();
    }
}
