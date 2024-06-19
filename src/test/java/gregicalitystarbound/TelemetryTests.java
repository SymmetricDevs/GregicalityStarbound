package gregicalitystarbound;

import com.starl0stgaming.gregicalitystarbound.api.telemetry.encryption.AuthKey;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.TelemetryConnection;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.TelemetryEndpoint;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.TelemetryPacket;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.data.TelemetryPacketPayload;
import net.minecraft.nbt.NBTTagCompound;
import org.junit.Test;

public class TelemetryTests {

    @Test
    public void test() {
        TelemetryConnection connection0 = new TelemetryConnection(0);

        TelemetryEndpoint endpoint0 = new TelemetryEndpoint(0, "disc0", new AuthKey(0, "disc0"), 8);
        TelemetryEndpoint endpoint1 = new TelemetryEndpoint(1, "disc1", new AuthKey(1, "disc1"), 16);
        TelemetryEndpoint endpoint2 = new TelemetryEndpoint(2, "disc2", new AuthKey(2, "disc2"), 1);

        connection0.addEndpoint(endpoint0);
        connection0.addEndpoint(endpoint1);
        connection0.addEndpoint(endpoint2);

        NBTTagCompound compound0 = new NBTTagCompound();
        compound0.setString("message", "Hello");

        NBTTagCompound compound1 = new NBTTagCompound();
        compound1.setString("answer", "Goodbye");

        TelemetryPacket packet0 = new TelemetryPacket(100);
        TelemetryPacketPayload payload0 = new TelemetryPacketPayload();
        payload0.setPayload(compound0);
        packet0.setDestinationID(1); // To endpoint 1
        packet0.setPacketPayload(payload0);

        TelemetryPacket packet1 = new TelemetryPacket(2000);
        TelemetryPacketPayload payload1 = new TelemetryPacketPayload();
        payload1.setPayload(compound1);
        packet1.setDestinationID(2);
        packet1.setPacketPayload(payload1);

        TelemetryPacket packet2 = new TelemetryPacket(2000);
        TelemetryPacketPayload payload2 = new TelemetryPacketPayload();
        payload2.setPayload(compound1);
        packet2.setDestinationID(2);
        packet2.setPacketPayload(payload2);

        connection0.sendPacketToDestination(packet2);
        connection0.sendPacketToDestination(packet0);
        endpoint1.sendPacket(packet1);

        endpoint1.update();
        endpoint2.update();

        NBTTagCompound bufCompound0 = endpoint1.getBufferedPayload().getPayload();
        NBTTagCompound bufCompound1 = endpoint2.getBufferedPayload().getPayload();

        System.out.println("Message is " + bufCompound0.getString("message"));
        System.out.println("Answer is " + bufCompound1.getString("answer"));
    }
}
