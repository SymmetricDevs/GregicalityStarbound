package com.starl0stgaming.gregicalitystarbound.api.telemetry;

import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.TelemetryNetwork;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;

public class TelemetryNetworkManager extends WorldSavedData {

    protected ArrayList<TelemetryNetwork> teleNetworks = new ArrayList<>();
    public TelemetryNetworkManager(String name) {
        super(name);
    }

    protected void addTeleNet(TelemetryNetwork tn) {
        teleNetworks.add(tn);
    }
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagList allNetworks = nbt.getTagList("GCSBNets", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < allNetworks.tagCount(); i++) {
            addTeleNet(new TelemetryNetwork(allNetworks.getCompoundTagAt(i)));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagList allNetworks = new NBTTagList();
        for (TelemetryNetwork net : teleNetworks) {
            NBTTagCompound netTag = net.serializeNBT();
            allNetworks.appendTag(netTag);
        }
        compound.setTag("GCSBNets", allNetworks);
        return compound;
    }
}
