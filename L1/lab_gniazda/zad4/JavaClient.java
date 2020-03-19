import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class JavaClient {

    public static void main(String args[]) throws Exception
    {
        System.out.println("ZAD4 - JAVA UDP CLIENT");
        DatagramSocket socket = null;
        int portNumber = 9010;

        try {
            socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("localhost");
            byte[] sendBuffer = new byte[1024];
            byte[] receiveBuffer = new byte[1024];

            //Sending
            sendBuffer = ByteBuffer.allocate(4).putInt(portNumber).array();
            System.out.println("SEND: " + portNumber);
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
            socket.send(sendPacket);

            //Receiving
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);
            String rcv = new String(receivePacket.getData());
            System.out.println("RECEIVE: " + rcv);
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
