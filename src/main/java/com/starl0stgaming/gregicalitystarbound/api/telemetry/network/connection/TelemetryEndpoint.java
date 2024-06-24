package com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.endpoint;

import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.TelemetryConnection;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.TelemetryPacket;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.data.TelemetryPacketPayload;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Comparator;
import java.util.PriorityQueue;

public class TelemetryEndpoint {

    // Ids are set in constructor, but it might be better to set them in TelemetryConnection
    private final int id;
    private TelemetryConnection connection;

    private PriorityQueue<TelemetryPacket> inPacketQueue;
    private PriorityQueue<TelemetryPacket> outPacketQueue;

    public TelemetryPacketPayload[] dataBuffer;

    protected String packetDiscriminator;
    protected AuthKey authKey;

    private boolean enableDiscriminator;
    private boolean enableEncryption;

    public TelemetryEndpoint(int id, String discriminator, AuthKey authKey) {
        this(id, discriminator, authKey, 32);
    }

    public TelemetryEndpoint(int id) {
        this.connection = null;
        this.id = id;
        this.dataBuffer = new TelemetryPacketPayload[8];
        this.bufferBeg = 0;
        inPacketQueue = new PriorityQueue<>(Comparator.comparingInt(packet -> -packet.getPriority()));
        outPacketQueue = new PriorityQueue<>(Comparator.comparingInt(packet -> -packet.getPriority()));


        inPacketQueue = new PriorityQueue<>(Comparator.comparingInt(packet -> -packet.getPriority()));
        outPacketQueue = new PriorityQueue<>(Comparator.comparingInt(packet -> -packet.getPriority()));
    }

    public void update() {
        //reads packet queues and processes in packets and sends out packets


        //read in packet queue
        if(!this.inPacketQueue.isEmpty())  {
            int payloadCount = bufferBeg;

            for(int i = 0; i < this.inPacketQueue.toArray().length; i++) {
                TelemetryPacket packetIn = this.inPacketQueue.poll();
                TelemetryPacketPayload packetPayload = packetIn.getPacketPayload();

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
    public TelemetryPacketPayload poll() {
        TelemetryPacketPayload payload = this.dataBuffer[0];

        //reshuffle array, probably shouldnt do this here though
        for(int i = 0; i < this.dataBuffer.length - 1; i++) {
            this.dataBuffer[i] = this.dataBuffer[i + 1];
        }
        return payload;
    }

    protected void receivePacket(TelemetryPacket packet) {
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

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setLong("id", this.id);
        linkedSystem.writeToNBT(nbt);
        return nbt;
    }
    public TelemetryConnection getNetwork() {
        return connection;
    }

    protected void setNetwork(TelemetryConnection network) {
        this.connection = network;
    }

    public void deserializeNBT(NBTTagCompound data) {
        this.id = data.getInteger("id");
        if (this.id > TelemetryNetworkManager.ENDPOINT_ID_COUNT) {
            TelemetryNetworkManager.ENDPOINT_ID_COUNT = this.id;
        }
    }

    public void setConnection(TelemetryConnection connection) {
        this.connection = connection;
    }

    public long getId() {
        return id;
    }

    public String getPacketDiscriminator() {
        return packetDiscriminator;
    }

    public void setPacketDiscriminator(String packetDiscriminator) {
        this.packetDiscriminator = packetDiscriminator;
    }

    public AuthKey getAuthKey() {
        return authKey;
    }

    public void setAuthKey(AuthKey authKey) {
        this.authKey = authKey;
    }
}
