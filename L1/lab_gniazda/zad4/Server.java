import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Arrays;
import java.nio.ByteBuffer;

public class Server {

    public static void main(String args[])
    {
        System.out.println("ZAD3 - JAVA UDP SERVER");
        DatagramSocket socket = null;
        int portNumber = 9010;

        try{
            socket = new DatagramSocket(portNumber);
            byte[] receiveBuffer = new byte[1024];
            byte[] sendBuffer = new byte[2014];

            //Receiving
            while(true) {
                //Receiving
                Arrays.fill(receiveBuffer, (byte)0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                SocketAddress address = receivePacket.getSocketAddress();
                int rcv = ByteBuffer.wrap(receiveBuffer).getInt();

                String lang = null;
                //Sending
                Arrays.fill(sendBuffer, (byte)0);
                if (rcv == portNumber) {
                    sendBuffer = "Pong Java".getBytes();
                    lang = "Java";
                } else {
                    sendBuffer = "Pong Python".getBytes();
                    lang = "Python";
                }
                System.out.println("RECEIVED: " + rcv + " FROM: " + address + " LANG: " + lang);
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address);
                socket.send(sendPacket);
            }
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
