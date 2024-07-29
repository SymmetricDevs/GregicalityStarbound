package com.starl0stgaming.gregicalitystarbound.api.telemetry.network;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import com.starl0stgaming.gregicalitystarbound.GregicalityStarbound;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.TelemetryConnection;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.endpoint.TelemetryEndpoint;

public class TelemetryNetworkManager extends WorldSavedData implements INBTSerializable<NBTTagCompound> {

    public static final String DATA_NAME = GregicalityStarbound.MODID + ".telemetryData";
    public static int ENDPOINT_ID_COUNT = 0;
    private static TelemetryNetworkManager INSTANCE;
    private final List<TelemetryEndpoint> endpointList;
    private List<TelemetryConnection> connectionList;

    public TelemetryNetworkManager() {
        super(DATA_NAME);
        this.connectionList = new ArrayList<>();
        this.endpointList = new ArrayList<>();
    }

    public TelemetryNetworkManager(String name) {
        super(name);
        this.connectionList = new ArrayList<>();
        this.endpointList = new ArrayList<>();
    }

    public static void setDirty() {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER && INSTANCE != null)
            INSTANCE.markDirty();
    }

    public static void setInstance(TelemetryNetworkManager tnm) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            INSTANCE = tnm;
        }
    }

    public void addConnection(TelemetryConnection connection) {
        this.connectionList.add(connection);
    }

    public void removeConnection(TelemetryConnection connection) {
        this.connectionList.remove(connection);
        TelemetryNetworkManager.setDirty();
    }

    public TelemetryConnection createConnection(int id) {
        TelemetryConnection conn = new TelemetryConnection(id);
        addConnection(conn);
        TelemetryNetworkManager.setDirty();
        return conn;
    }

    public TelemetryEndpoint createEndpoint() {
        TelemetryEndpoint endpoint = new TelemetryEndpoint(ENDPOINT_ID_COUNT + 1);
        endpointList.add(endpoint);
        TelemetryNetworkManager.setDirty();
        ENDPOINT_ID_COUNT++;
        return endpoint;
    }

    public List<TelemetryConnection> getConnections() {
        return this.connectionList;
    }

    public List<TelemetryEndpoint> getEndpoints() {
        return this.endpointList;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagCompound ntc = new NBTTagCompound();
        NBTTagList ntList = new NBTTagList();
        for (TelemetryConnection tc : connectionList) {
            ntList.appendTag(tc.serializeNBT());
        }
        ntc.setTag("connections", ntList);
        nbt.setTag("GCSBnets", ntc);
        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.connectionList = new ArrayList<>();
        NBTTagList connData = nbt.getTagList("GCSBnets", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < connData.tagCount(); i++) {
            connectionList.add(new TelemetryConnection(connData.getCompoundTagAt(i)));
        }
    }
}
