import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.SQLOutput;

public class UDPClient {
    private final DatagramSocket socket;
    private final InetAddress address;
    private final int port;

    public UDPClient(int port) throws IOException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
        this.port = port;
    }

    public void send(String msg) {
        var msgBuf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(msgBuf, msgBuf.length, address, port);
        try {
            socket.setSoTimeout(3000);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("Server listening on port " + args[0]);
            System.out.println("Sensor data: " + args[1]);
//          new UDPClient(3344).send("sensor_id=t1;value=35.5"); "sensor_id=h1;value=50.1"
            new UDPClient(Integer.parseInt(args[0])).send(args[1]);
            System.out.println("Exiting...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
