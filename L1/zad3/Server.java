import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Arrays;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Server {

    public static void main(String args[])
    {
        System.out.println("ZAD3 - JAVA UDP SERVER");
        DatagramSocket socket = null;
        int portNumber = 9009;

        try{
            socket = new DatagramSocket(portNumber);
            byte[] receiveBuffer = new byte[1024];
            byte[] sendBuffer = new byte[1024];

            //Receiving
            Arrays.fill(receiveBuffer, (byte)0);
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);
            int rcv = ByteBuffer.wrap(receiveBuffer).order(ByteOrder.LITTLE_ENDIAN).getInt();
            SocketAddress address = receivePacket.getSocketAddress();
            System.out.println("RECEIVED: " + rcv + " FROM: " + address);

            //Sending
            sendBuffer = ByteBuffer.allocate(4).putInt(++rcv).array();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address);
            socket.send(sendPacket);
            System.out.println("SEND: " + rcv);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
