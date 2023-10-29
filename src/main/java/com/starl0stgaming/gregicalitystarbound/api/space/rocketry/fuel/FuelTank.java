package com.starl0stgaming.gregicalitystarbound.api.space.rocketry.fuel;

public class FuelTank {


    private int maxPropellantCapacity;
    private int maxPressureCapacity;

    private int propellant;
    private int pressure;


    public FuelTank() {

    }

    public void addPropellantToTank(int amountToAdd) {
        if(this.maxPropellantCapacity > (this.propellant + amountToAdd)) {
            this.propellant += amountToAdd;
        }
    }


    //TODO: Maybe do as a part of a FuelTransfer an operation result, and return false if the amount to be removed exceeds the current stored amount
    public void removePropellantFromTank(int amountToRemove) {
        if(this.getPropellant() < amountToRemove) {
            this.propellant =- this.getPropellant(); // :troll:
        } else if(this.getPropellant() > amountToRemove) {
            this.propellant -= amountToRemove;
        }
    }

    public int getMaxPropellantCapacity() {
        return maxPropellantCapacity;
    }

    public void setMaxPropellantCapacity(int maxPropellantCapacity) {
        this.maxPropellantCapacity = maxPropellantCapacity;
    }

    public int getMaxPressureCapacity() {
        return maxPressureCapacity;
    }

    public void setMaxPressureCapacity(int maxPressureCapacity) {
        this.maxPressureCapacity = maxPressureCapacity;
    }

    public int getPropellant() {
        return propellant;
    }

    public void setPropellant(int propellant) {
        this.propellant = propellant;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }
}
