package com.starl0stgaming.gregicalitystarbound.api.util;

import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.fuel.network.FuelNetwork;

public class FuelUtil {


    //this could be shortened so much if I fixed the network fuel utils but whatever
    public int transferFuelFromNetworkToNetwork(int amountToTransfer, FuelNetwork networkFrom, FuelNetwork networkTo) {
         int amountInNetwork1 = networkFrom.getCurrentStoredFuel();
         networkFrom.removeFuelFromNetwork(amountToTransfer);

         int amountRemoved = amountInNetwork1 - networkFrom.getCurrentStoredFuel();
         if(amountRemoved < amountToTransfer) {
             //idk cry about it
         }

         int amountInNetwork2 = networkTo.getCurrentStoredFuel();
         networkTo.addFuelToNetwork(amountRemoved);

         int amountAdded = amountInNetwork2 - networkTo.getCurrentStoredFuel();
         if(amountAdded < amountToTransfer) {
             //cry about it
         }

         return amountToTransfer;
    }
}
