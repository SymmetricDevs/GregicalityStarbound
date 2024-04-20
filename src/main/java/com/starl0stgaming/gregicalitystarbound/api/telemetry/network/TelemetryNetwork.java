package com.starl0stgaming.gregicalitystarbound.api.telemetry.network;

import com.starl0stgaming.gregicalitystarbound.api.telemetry.TelemetryNetworkManager;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.TelemetryConnection;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.List;

public class TelemetryNetwork implements INBTSerializable<NBTTagCompound> {

    private List<TelemetryConnection> connectionList;

    public TelemetryNetwork(NBTTagCompound worldData) {
        this.connectionList = new ArrayList<>();
        NBTTagList connData = worldData.getTagList("connections", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < connData.tagCount(); i++) {
            connectionList.add(new TelemetryConnection(connData.getCompoundTagAt(i)));
        }
    }

    public void addConnection(TelemetryConnection connection) {
        this.connectionList.add(connection);
    }

    public void removeConnection(TelemetryConnection connection) {
        this.connectionList.add(connection);
    }

    public List<TelemetryConnection> getConnections() {
        return this.connectionList;
    }


    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound ntc = new NBTTagCompound();
        NBTTagList ntList = new NBTTagList();
        for (TelemetryConnection tc : connectionList) {
            ntList.appendTag(tc.serializeNBT());
        }
        ntc.setTag("connections", ntList);
        return ntc;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.connectionList = new ArrayList<>();
        NBTTagList connData = nbt.getTagList("connections", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < connData.tagCount(); i++) {
            connectionList.add(new TelemetryConnection(connData.getCompoundTagAt(i)));
        }
    }
}
