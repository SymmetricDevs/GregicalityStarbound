package com.starl0stgaming.gregicalitystarbound.api.space.planets;

public class Planet {

    //Defining variables
    private String planetName;
    private int id;
    private int dimID;


    private boolean isLoaded;





    //Atmosphere


    //Terrain Gen


    public Planet(int id, String planetName) {
        this.id = id;
        this.planetName = planetName;
    }

    public Planet(int id, int dimID, String planetName) {
        this.id = id;
        this.dimID = dimID;
        this.planetName = planetName;
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
