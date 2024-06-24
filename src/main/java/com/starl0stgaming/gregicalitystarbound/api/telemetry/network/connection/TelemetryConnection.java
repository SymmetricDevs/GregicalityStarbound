package com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection;

import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.endpoint.TelemetryEndpoint;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.TelemetryPacket;

import java.util.ArrayList;
import java.util.List;

public class TelemetryConnection {

    /* Map is more suitable for this job because
     * - You can't add two end points with same ids
     * - It's faster to reach endpoints like this
     *
     * Lists might be used if ids are going to be handled in connections
     **/
    private final Int2ObjectMap<TelemetryEndpoint> endpointMap;

    public TelemetryConnection(int id) {
        this.endpointMap = new Int2ObjectOpenHashMap<>();
    }

    public TelemetryConnection(NBTTagCompound nbtBase) {
        deserializeNBT(nbtBase);
    }


    public void sendPacketToNetwork(TelemetryPacket telemetryPacket) {
        if (endpointMap.isEmpty()) return;
        for (int i : endpointMap.keySet()) {
            TelemetryEndpoint endpoint = endpointMap.get(i);
            endpoint.receivePacket(telemetryPacket);
            GCSBLog.LOGGER.info("Sent packet to endpoint with id " + endpoint.getId());
        }
    }

    public void sendPacketToDestination(TelemetryPacket telemetryPacket) {
        if (this.endpointMap.isEmpty()) return;
        for (int i : endpointMap.keySet()) {
            if (i == telemetryPacket.getDestinationID()) {
                TelemetryEndpoint endpoint = endpointMap.get(i);
                endpoint.receivePacket(telemetryPacket);
                GCSBLog.LOGGER.info("Sent packet to endpoint with id " + endpoint.getId());
            }
        }
    }

    public void addEndpoint(TelemetryEndpoint endpoint) {
        endpointMap.put(endpoint.getId(), endpoint);

        if (endpoint.getNetwork() != null) {
            endpoint.getNetwork().removeEndpoint(endpoint);
        }

        endpoint.setNetwork(this);
    }

    public void removeEndpoint(TelemetryEndpoint endpoint) {
        this.endpointMap.remove(endpoint.getId());
    }

    public TelemetryEndpoint getEndpointByID(int id) {
        return this.endpointMap.get(id);
    }

    public List<TelemetryEndpoint> getEndpointList() {
        return ImmutableList.copyOf(endpointMap.values());
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
