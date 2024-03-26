package gregicalitystarbound;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.TelemetryConnection;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.connection.endpoint.TelemetryEndpoint;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.TelemetryPacket;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.packet.data.TelemetryPacketPayload;
import net.minecraft.nbt.NBTTagCompound;
import org.junit.BeforeClass;
import org.junit.Test;

public class TelemetryTests {
    @BeforeClass
    public static void bootstrap() {

    }

    @Test
    public void packetSendAndReceive() {
        System.out.println("Testing is happening!");
        TelemetryEndpoint endpointSender = new TelemetryEndpoint(0);
        TelemetryEndpoint epReceiver1 = new TelemetryEndpoint(1);
        TelemetryEndpoint epReceiver2 = new TelemetryEndpoint(2);
        TelemetryConnection tcMain = new TelemetryConnection(0);
        tcMain.addEndpoints(endpointSender, epReceiver1, epReceiver2);
        TelemetryPacket tp0 = new TelemetryPacket(0);
        NBTTagCompound relayedData = new NBTTagCompound();
        relayedData.setString("test", "Hello, World!");
        tp0.setPacketData(new TelemetryPacketPayload(relayedData));
        tp0.setDestinationID(2);
        endpointSender.sendPacket(tp0);
        endpointSender.update();
        epReceiver2.update();
        assert(epReceiver2.poll().getPayload().getString("test")).equals("Hello, World!");
        tp0.setDestinationID(1);
        endpointSender.sendPacket(tp0);
        endpointSender.update();
        epReceiver1.update();
        assert(epReceiver1.poll().getPayload().getString("test")).equals("Hello, World!");
    }
}
