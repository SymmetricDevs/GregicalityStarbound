package com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection;

import com.google.common.collect.ImmutableList;
import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.TelemetryNetworkManager;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.endpoint.TelemetryEndpoint;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.TelemetryPacket;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.List;

public class TelemetryConnection {

    /* Map is more suitable for this job because
     * - You can't add two end points with same ids
     * - It's faster to reach endpoints like this
     *
     * Lists might be used if ids are going to be handled in connections
     **/
    private final Int2ObjectMap<TelemetryEndpoint> endpointMap;
    private final int id;

    public TelemetryConnection(int id) {
        this.id = id;
        this.endpointMap = new Int2ObjectOpenHashMap<>();
    }

    public TelemetryConnection(NBTTagCompound nbtBase) {
        this.id = nbtBase.getInteger("id");
        this.endpointMap = new Int2ObjectOpenHashMap<>();
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

    public void addEndpoints(TelemetryEndpoint... endpoints) {
        for(TelemetryEndpoint ep: endpoints) {
            endpointMap.put(ep.getId(), ep);
            if (ep.getNetwork() != null) {
                ep.getNetwork().removeEndpoint(ep);
            }

            ep.setNetwork(this);
        }
        TelemetryNetworkManager.setDirty();
    }

    public void removeEndpoint(TelemetryEndpoint endpoint) {
        this.endpointMap.remove(endpoint.getId());
        TelemetryNetworkManager.setDirty();
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
        for (TelemetryEndpoint ep: getEndpointList()) {
            ntcList.appendTag(ep.serializeNBT());
        }
        ntc.setTag("endpoints", ntcList);
        ntc.setInteger("id", this.id);
        return ntc;
    }

    public void deserializeNBT(NBTTagCompound data) {
        NBTTagList epList = data.getTagList("endpoints", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < epList.tagCount(); i++) {
            endpointMap.put(epList.getCompoundTagAt(i).getInteger("id"), new TelemetryEndpoint(epList.getCompoundTagAt(i)));
        }
    }
}
