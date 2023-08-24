package com.starl0stgaming.gregicalitystarbound.api.space.planets;

public class Planet {

    //Defining variables
    private int id;
    private int dimID;
    private boolean isLoaded;


    private String planetName;


    //Atmosphere


    //Terrain Gen


    public Planet(int id, String planetName) {
        this.id = id;
        this.planetName = planetName;
    }

    //Leaving this here for future reference, may use it, may not use it.
    //Return true if planet wasn't loaded, returns false if it already is
    public boolean load() {
        if(!this.isLoaded) {
            this.isLoaded = true;
            return true;
        } else {
            return false;
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
}
