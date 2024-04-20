package gregicalitystarbound;
import com.starl0stgaming.gregicalitystarbound.api.telemetry.network.TelemetryNetworkManager;
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
        TelemetryNetworkManager tnm = new TelemetryNetworkManager("tnm");
        TelemetryEndpoint endpointSender = tnm.createEndpoint();
        TelemetryEndpoint epReceiver1 = tnm.createEndpoint();
        TelemetryEndpoint epReceiver2 = tnm.createEndpoint();
        TelemetryConnection tcMain = tnm.createConnection();
        tcMain.addEndpoints(endpointSender, epReceiver1, epReceiver2);
        TelemetryPacket tp0 = new TelemetryPacket(0);
        NBTTagCompound relayedData = new NBTTagCompound();
        relayedData.setString("test", "Hello, World!");
        tp0.setPacketData(new TelemetryPacketPayload(relayedData));
        tp0.setDestinationID(epReceiver2.getId());
        endpointSender.sendPacket(tp0);
        endpointSender.update();
        epReceiver2.update();
        assert(epReceiver2.poll().getPayload().getString("test")).equals("Hello, World!");
        tp0.setDestinationID(epReceiver1.getId());
        endpointSender.sendPacket(tp0);
        endpointSender.update();
        epReceiver1.update();
        assert(epReceiver1.poll().getPayload().getString("test")).equals("Hello, World!");
    }
}
