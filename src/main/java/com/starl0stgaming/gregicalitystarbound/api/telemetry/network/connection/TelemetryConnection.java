package com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection;

import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.endpoint.TelemetryEndpoint;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.TelemetryPacket;

import java.util.ArrayList;
import java.util.List;

public class TelemetryConnection {

    private List<TelemetryEndpoint> endpointList;


    public TelemetryConnection(int id) {
        this.endpointList = new ArrayList<>();
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
}
