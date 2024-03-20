package com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.endpoint;

import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.TelemetryConnection;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.TelemetryPacket;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.data.TelemetryPacketPayload;

import java.util.Comparator;
import java.util.PriorityQueue;

public class TelemetryEndpoint {

    private int id;
    private TelemetryConnection connection;

    private PriorityQueue<TelemetryPacket> inPacketQueue;
    private PriorityQueue<TelemetryPacket> outPacketQueue;

    public TelemetryPacketPayload[] dataBuffer;



    //private Discriminator discriminator; or
    //private AuthKey authKey;

    public TelemetryEndpoint(int id) {
        this.connection = null;
        this.id = id;

        inPacketQueue = new PriorityQueue<>(Comparator.comparingInt(packet -> -packet.getPriority()));
        outPacketQueue = new PriorityQueue<>(Comparator.comparingInt(packet -> -packet.getPriority()));
    }

    public void update() {
        //reads packet queues and processes in packets and sends out packets


        //read in packet queue
        if(!this.inPacketQueue.isEmpty())  {
            int payloadCount = 0;

            for(int i = 0; i < this.inPacketQueue.toArray().length; i++) {
                TelemetryPacket packetIn = this.inPacketQueue.poll();
                TelemetryPacketPayload packetPayload = packetIn.getPacketData();

                boolean hasPayloadBeenAllocated = false;

                while(hasPayloadBeenAllocated) {
                    if(this.dataBuffer[payloadCount] == null) {
                        this.dataBuffer[payloadCount] = packetPayload;
                        payloadCount++;
                        hasPayloadBeenAllocated = true;
                    } else {
                        payloadCount++;
                    }
                }
            }
        }

        //read out packet queue and send packets to specified destination in packet info or whole network
        if(!this.outPacketQueue.isEmpty()) {
            for(int i = 0; i < this.outPacketQueue.toArray().length; i++) {
                this.connection.sendPacketToDestination(this.outPacketQueue.poll());
            }
        }
    }

    /**
     *
     * @return returns the TelemetryPacketPayload in the index 0 of the endpoint's data buffer.
     */
    public TelemetryPacketPayload getBufferedPayload() {
        TelemetryPacketPayload payload = this.dataBuffer[0];

        //reshuffle array, probably shouldnt do this here though
        for(int i = 0; i < this.dataBuffer.length - 1; i++) {
            this.dataBuffer[i + 1] = this.dataBuffer[i];
        }
        return payload;
    }

    public void receivePacket(TelemetryPacket packet) {
        this.inPacketQueue.add(packet);
        GCSBLog.LOGGER.info("Received packet on endpoint with id " + id);
    }

    public void sendPacket(TelemetryPacket packet) {
        if(this.connection == null) {
            GCSBLog.LOGGER.error("[ERROR] Telemetry Endpoint with id " + this.getId() + " cant send packet because it has no bound network!");
            return;
        }
        this.outPacketQueue.add(packet);
        GCSBLog.LOGGER.info("Sent packet from endpoint with id " + id);
    }

    public void setNetwork(TelemetryConnection network) {
        this.connection = network;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
