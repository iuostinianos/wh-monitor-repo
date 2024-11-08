package ellas.wh.datacollector.server;

import ellas.wh.common.models.HumidityData;
import ellas.wh.common.models.SensorData;
import ellas.wh.common.models.TemperatureData;
import ellas.wh.datacollector.config.UDPServerProperties;
import ellas.wh.messaging.akka.DataSender;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

@Slf4j
public class UDPServer extends Thread {
    private final DatagramSocket socket;
    private final DataSender dataSender;
    private final String sensorIdPrefix;

    public UDPServer(UDPServerProperties properties, DataSender dataSender) throws SocketException {
        super(properties.serverName());
        this.socket = new DatagramSocket(properties.serverPort());
        this.dataSender = dataSender;
        sensorIdPrefix = properties.sensorIdPrefix();
        setDaemon(true);
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        log.info("UDP server[{}] is up and running!", Thread.currentThread().getName());

        while (true) {
            byte[] buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
                dataSender.send(PacketDataConverter.convert(packet.getData(), sensorIdPrefix));
            } catch (IOException ignore) {
            }
        }
    }

    // used as a shutdown callback
    @SuppressWarnings("unused")
    public void close() {
        socket.close();
        log.info("Server closed!");
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class PacketDataConverter {

        static SensorData convert(byte[] data, String sensorIdPrefix) {
            // sensor_id=t1;value=20
            var packet = new String(data).trim().split(";");
            if (packet.length != 2) {
                throw new IllegalArgumentException("Illegal: " + Arrays.toString(packet));
            }
            var sensorIdPart = packet[0].split("=");
            if (sensorIdPart.length != 2) {
                throw new IllegalArgumentException("Illegal: " + Arrays.toString(sensorIdPart));
            }
            var sensorValuePart = packet[1].split("=");
            if (sensorValuePart.length != 2) {
                throw new IllegalArgumentException("Illegal: " + Arrays.toString(sensorValuePart));
            }
            return sensorData(sensorIdPrefix, sensorIdPart[1], sensorValuePart[1]);
        }

        static SensorData sensorData(String sensorIdPrefix, String sensorId, String sensorValue) {
            switch (sensorIdPrefix) {
                case "t":
                    if (sensorId.startsWith(sensorIdPrefix)) {
                        return new TemperatureData(Double.parseDouble(sensorValue));
                    }
                case "h":
                    if (sensorId.startsWith(sensorIdPrefix)) {
                        return new HumidityData(Double.parseDouble(sensorValue));
                    }
            }
            throw new IllegalArgumentException("Unknown sensor id [" + sensorId + "]");
        }
    }
}