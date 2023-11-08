package com.starl0stgaming.gregicalitystarbound.api.space.rocketry.fuel;

import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;

public class FuelTank {


    private int maxPropellantCapacity;
    private int maxPressureCapacity;

    private int propellant;
    private int pressure;


    private boolean isFull;
    private boolean isEmpty;


    public FuelTank(int maxPropellantCapacity, int maxPressureCapacity) {
        this.maxPropellantCapacity = maxPropellantCapacity;
        this.maxPressureCapacity = maxPressureCapacity;
    }

    //TODO: could use the isFull variable to determine if its able to take more fuel in
    public int addPropellantToTank(int amountToAdd) {
        if(this.maxPropellantCapacity > (this.propellant + amountToAdd)) {
            this.propellant += amountToAdd;
            if(this.propellant == this.maxPropellantCapacity && !this.isFull) {
                this.setFull(true);
            }
            return amountToAdd;
        } else if(this.maxPropellantCapacity < (this.propellant + amountToAdd)) {
            int amountAdded = this.maxPropellantCapacity - this.propellant;
            this.propellant += amountAdded;
            if(this.propellant == this.maxPropellantCapacity && !this.isFull) {
                this.setFull(true);
            }
            return amountAdded;
        }

        return 0;
    }


    //TODO: Maybe do as a part of a FuelTransfer an operation result, and return false if the amount to be removed exceeds the current stored amount
    public int removePropellantFromTank(int amountToRemove) {
        if(this.getPropellant() < amountToRemove) {
            int amountRemoved = this.getPropellant();
            this.propellant =- amountRemoved; // :troll:
            if(this.getPropellant() == 0 && !this.isEmpty) {
                this.setEmpty(true);
            }
            return amountRemoved;
        } else if(this.getPropellant() > amountToRemove) {
            int amountRemoved = this.propellant - amountToRemove;
            this.propellant -= amountRemoved;
            if(this.getPropellant() == 0 && !this.isEmpty) {
                this.setEmpty(true);
            }
            return amountRemoved;
        }

        return 0;
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

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
}
