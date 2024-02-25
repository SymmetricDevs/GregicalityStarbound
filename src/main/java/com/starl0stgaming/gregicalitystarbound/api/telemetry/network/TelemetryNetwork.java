package com.starl0stgaming.gregicalitystarbound.api.telemetry.network;

import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.TelemetryConnection;

import java.util.ArrayList;
import java.util.List;

public class TelemetryNetwork {

    private List<TelemetryConnection> connectionList;

    public TelemetryNetwork() {
        this.connectionList = new ArrayList<>();
    }

    public void addConnection(TelemetryConnection connection) {
        this.connectionList.add(connection);
    }

    public void removeConnection(TelemetryConnection connection) {
        this.connectionList.add(connection);
    }
}
