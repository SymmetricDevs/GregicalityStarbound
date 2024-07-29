package com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet;

import com.starl0stgaming.gregicalitystarbound.api.telemetry.encryption.AuthKey;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.data.TelemetryPacketPayload;

public class TelemetryPacket {

    private int priority;
    // gets cleared after each send operation is completed, if value equals 0 then it will be sent to whole connection;
    private int destinationID;
    private TelemetryPacketPayload packetPayload;

    private String discriminator;
    private AuthKey authKey;

    private boolean enableEncryption;

    public TelemetryPacket(int priority) {
        this.priority = priority;
    }

    public AuthKey getAuthKey() {
        return authKey;
    }

    public void setAuthKey(AuthKey authKey) {
        this.authKey = authKey;
    }

    public boolean isEncrypted() {
        return enableEncryption;
    }

    public void setEncryptionState(boolean enableEncryption) {
        this.enableEncryption = enableEncryption;
    }

    public TelemetryPacketPayload getPacketPayload() {
        return packetPayload;
    }

    public void setPacketPayload(TelemetryPacketPayload packetData) {
        this.packetPayload = packetData;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getDestinationID() {
        return destinationID;
    }

    public void setDestinationID(int destinationID) {
        this.destinationID = destinationID;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }
}
