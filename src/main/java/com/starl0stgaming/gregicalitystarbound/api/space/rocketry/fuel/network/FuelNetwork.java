package com.starl0stgaming.gregicalitystarbound.api.space.rocketry.fuel.network;

import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.fuel.FuelTank;

import java.util.ArrayList;
import java.util.List;

public class FuelNetwork {

    private int id;

    private List<FuelTank> fuelTankList;

    private int fuelFlowRate;

    public FuelNetwork(int id) {
        this.id = id;
        this.fuelTankList = new ArrayList<>();
    }


    public void init() {

    }


    public void update() {

    }


    public void addFuelToNetwork(int totalAmountToAdd) {
        int amountToAdd = totalAmountToAdd;
        for (int i = 0; i < this.getFuelTankList().toArray().length; i++) {
            FuelTank fuelTank = this.getFuelTankList().get(i);
            if (!fuelTank.isFull() && amountToAdd > 0) {
                int amountAdded = fuelTank.addPropellantToTank(amountToAdd);
                amountToAdd = -amountAdded;
                if (amountToAdd <= 0) {
                    break;
                }
            } else {
                GCSBLog.LOGGER.info("[FuelNetwork] Attempted to add fuel to fuel tank, but tank is full");
            }
        }
    }

    public void removeFuelFromNetwork(int totalAmountToRemove) {
        int amountToRemove = totalAmountToRemove;
        for (int i = 0; i < this.getFuelTankList().toArray().length; i++) {
            FuelTank fuelTank = this.fuelTankList.get(i);
            if (!fuelTank.isEmpty() && amountToRemove > 0) {
                int amountRemoved = fuelTank.removePropellantFromTank(amountToRemove);
                amountToRemove = -amountRemoved;
                if (amountToRemove <= 0) {
                    break;
                }
            } else {
                GCSBLog.LOGGER.info("[FuelNetwork] Attempted to remove fuel from fuel tank, but tank is empty or operation has been completed");
            }
        }
    }

    public int getMaximumFuelCapacity() {
        int maxFuelCapacity = 0;
        for (int i = 0; i < getFuelTankList().toArray().length; i++) {
            FuelTank fuelTank = getFuelTankList().get(i);

            maxFuelCapacity += fuelTank.getMaxPropellantCapacity();
        }
        return maxFuelCapacity;
    }

    public int getCurrentStoredFuel() {
        int currentStoredFuel = 0;
        for (int i = 0; i < getFuelTankList().toArray().length; i++) {
            FuelTank fuelTank = getFuelTankList().get(i);

            currentStoredFuel += fuelTank.getPropellant();
        }
        return currentStoredFuel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<FuelTank> getFuelTankList() {
        return fuelTankList;
    }

    public void setFuelTankList(List<FuelTank> fuelTankList) {
        this.fuelTankList = fuelTankList;
    }

    public void addFuelTank(FuelTank fuelTank) {
        this.fuelTankList.add(fuelTank);
    }
}
