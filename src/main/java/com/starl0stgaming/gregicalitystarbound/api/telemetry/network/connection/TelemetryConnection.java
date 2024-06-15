package com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection;

import com.google.common.collect.ImmutableList;
import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.TelemetryPacket;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.*;

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
}
