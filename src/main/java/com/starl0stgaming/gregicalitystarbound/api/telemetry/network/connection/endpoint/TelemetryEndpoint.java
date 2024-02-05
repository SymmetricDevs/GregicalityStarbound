package com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.endpoint;

import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.PacketQueue;

public class TelemetryEndpoint {

    private int id;

    private PacketQueue packetQueue;

    //private Discriminator discriminator; or
    //private AuthKey authKey;

    public TelemetryEndpoint(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
