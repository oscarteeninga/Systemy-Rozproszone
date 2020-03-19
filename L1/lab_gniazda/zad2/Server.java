import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Arrays;
import java.nio.ByteBuffer;

public class Server {

    public static void main(String args[])
    {
        System.out.println("ZAD2 - JAVA UDP SERVER");
        DatagramSocket socket = null;
        int portNumber = 9008;

        try{
            socket = new DatagramSocket(portNumber);
            byte[] receiveBuffer = new byte[1024];
            byte[] sendBuffer = new byte[1024];

            //Receiving
            Arrays.fill(receiveBuffer, (byte)0);
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);
            String rcv = new String(receivePacket.getData());
            SocketAddress address = receivePacket.getSocketAddress();
            System.out.println("RECEIVED: " + rcv + " FROM: " + address);

            //Sending
            sendBuffer = rcv.getBytes();
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
