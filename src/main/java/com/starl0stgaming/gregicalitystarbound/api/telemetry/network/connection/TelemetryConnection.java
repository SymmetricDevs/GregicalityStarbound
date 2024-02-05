package com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection;

import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.endpoint.TelemetryEndpoint;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.TelemetryPacket;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.data.TelemetryPacketData;

import java.util.ArrayList;
import java.util.List;

public class TelemetryConnection {

    private List<TelemetryEndpoint> endpointList;


    public TelemetryConnection(int id) {
        this.endpointList = new ArrayList<>();
    }


    public static void sendPacketToNetwork(TelemetryPacket telemetryPacket, int priority) {

    }

    public void addEndpoint(TelemetryEndpoint endpoint) {
        this.endpointList.add(endpoint);
    }

    public void removeEndpoint(TelemetryEndpoint endpoint) {
        this.endpointList.remove(endpoint);
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
