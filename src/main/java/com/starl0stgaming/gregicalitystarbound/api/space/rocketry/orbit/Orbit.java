package com.starl0stgaming.gregicalitystarbound.api.space.rocketry.orbit;

public class Orbit {

    //we are assuming perfect circular orbits for simplicity's sake, for now.
    private int orbitalHeight;
    // the id of the planet this orbit is set in
    private int planetID;

    public Orbit() {

    }

    public Orbit build() {
        return this;
    }
    public int getOrbitalHeight() {
        return orbitalHeight;
    }

    public void setOrbitalHeight(int orbitalHeight) {
        this.orbitalHeight = orbitalHeight;
    }

    public int getPlanetID() {
        return planetID;
    }

    public void setPlanetID(int planetID) {
        this.planetID = planetID;
    }
}
