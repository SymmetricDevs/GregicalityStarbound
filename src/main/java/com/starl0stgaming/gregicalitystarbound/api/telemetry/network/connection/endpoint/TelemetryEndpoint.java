package com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.endpoint;

public class TelemetryEndpoint {

    private int id;

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
