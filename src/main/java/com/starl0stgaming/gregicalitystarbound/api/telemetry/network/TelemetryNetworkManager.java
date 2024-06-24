package com.starl0stgaming.gregicalitystarbound.api.telemetry.network;

import com.starl0stgaming.gregicalitystarbound.GregicalityStarbound;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.TelemetryConnection;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.endpoint.TelemetryEndpoint;
import gregtech.api.GTValues;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;

public class TelemetryNetworkManager extends WorldSavedData implements INBTSerializable<NBTTagCompound> {

    public static final String dataName = GregicalityStarbound.MODID + ".telemetryData";
    private static TelemetryNetworkManager INSTANCE;
    public static long ENDPOINT_ID_COUNT = 0;
    private final List<TelemetryEndpoint> endpointList;
    private List<TelemetryConnection> connectionList;

    public TelemetryNetworkManager(String name) {
        super(name);
        this.connectionList = new ArrayList<>();
        this.endpointList = new ArrayList<>();
    }

    public void addConnection(TelemetryConnection connection) {
        this.connectionList.add(connection);
    }

    public void removeConnection(TelemetryConnection connection) {
        this.connectionList.remove(connection);
    }

    public TelemetryConnection createConnection() {
        TelemetryConnection conn = new TelemetryConnection();
        addConnection(conn);
        return conn;
    }

    public TelemetryEndpoint createEndpoint() {
        TelemetryEndpoint endpoint = new TelemetryEndpoint(ENDPOINT_ID_COUNT+1);
        endpointList.add(endpoint);
        ENDPOINT_ID_COUNT++;
        return endpoint;
    }

    public List<TelemetryConnection> getConnections() {
        return this.connectionList;
    }
    public List<TelemetryEndpoint> getEndpoints() { return this.endpointList; }


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

    public static void setInstance(TelemetryNetworkManager tnm)  {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            INSTANCE = tnm;
        }
    }
}
