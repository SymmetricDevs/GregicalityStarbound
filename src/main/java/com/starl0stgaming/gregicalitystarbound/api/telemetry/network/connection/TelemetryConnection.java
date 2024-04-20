package com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection;

import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.endpoint.TelemetryEndpoint;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.TelemetryPacket;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.List;

public class TelemetryConnection implements INBTSerializable<NBTTagCompound> {

    private List<TelemetryEndpoint> endpointList;


    public TelemetryConnection() {
        this.endpointList = new ArrayList<>();
    }

    public TelemetryConnection(NBTTagCompound nbtBase) {
        deserializeNBT(nbtBase);
    }


    public void sendPacketToNetwork(TelemetryPacket telemetryPacket) {
        if(this.endpointList.isEmpty()) return;
        for(int i = 0; i < this.endpointList.toArray().length; i++) {
            TelemetryEndpoint endpoint = this.endpointList.get(i);
            endpoint.receivePacket(telemetryPacket);
            GCSBLog.LOGGER.info("Sent packet to endpoint with id " + endpoint.getId());
        }
    }

    public void sendPacketToDestination(TelemetryPacket telemetryPacket) {
        if(this.endpointList.isEmpty()) return;
        for(int i = 0; i < this.endpointList.toArray().length; i++) {
            if(this.endpointList.get(i).getId() == telemetryPacket.getDestinationID()) {
                TelemetryEndpoint endpoint = this.endpointList.get(i);
                endpoint.receivePacket(telemetryPacket);
                GCSBLog.LOGGER.info("Sent packet to endpoint with id " + endpoint.getId());
            } else if(telemetryPacket.getDestinationID() == 0) {
                this.sendPacketToNetwork(telemetryPacket);
            }
        }
    }

    public void addEndpoint(TelemetryEndpoint endpoint) {
        this.endpointList.add(endpoint);
        endpoint.setConnection(this);
    }

    public void addEndpoints(TelemetryEndpoint... endpoints) {
        for (TelemetryEndpoint endpoint : endpoints) {
            this.addEndpoint(endpoint);
        }
    }

    public void removeEndpoint(TelemetryEndpoint endpoint) {
        this.endpointList.remove(endpoint);
        endpoint.setConnection(null);
    }

    public TelemetryEndpoint getEndpointByID(int id) {
        for(int i = 0; i < this.endpointList.toArray().length; i++) {
            if(this.endpointList.get(i).getId() == id) {
                return this.endpointList.get(i);
            }
        }

        return null;
    }

    public List<TelemetryEndpoint> getEndpointList() {
        return endpointList;
    }

    public NBTTagCompound serializeNBT() {
        NBTTagCompound ntc = new NBTTagCompound();
        NBTTagList ntcList = new NBTTagList();
        for (TelemetryEndpoint ep: endpointList) {
            ntcList.appendTag(ep.serializeNBT());
        }
        ntc.setTag("endpoints", ntcList);
        return ntc;
    }

    public void deserializeNBT(NBTTagCompound data) {
        this.endpointList = new ArrayList<>();
        NBTTagList epList = data.getTagList("endpoints", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < epList.tagCount(); i++) {
            endpointList.add(new TelemetryEndpoint(epList.getCompoundTagAt(i)));
        }
    }
}
