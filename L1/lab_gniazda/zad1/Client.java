import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class Client {

    public static void main(String args[]) throws Exception
    {
        System.out.println("ZAD1 - JAVA UDP CLIENT");
        DatagramSocket socket = null;
        int portNumber = 9000;

        try {
            socket = new DatagramSocket();
            InetAddress address = InetAddress.getByName("localhost");
            byte[] receiveBuffer = new byte[1024];
            byte[] sendBuffer = new byte[1024];

            for(int i = 0;;i++) {
                //Sending
                String snd = "PING " + i;
                sendBuffer = snd.getBytes();
                System.out.println("SEND: " + snd);
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
                socket.send(sendPacket);

                
                //Receiving
                Arrays.fill(receiveBuffer, (byte)0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                String rcv = new String(receivePacket.getData());
                System.out.println("RECEIVED: " + rcv + " FROM: " + receivePacket.getSocketAddress());
                Thread.sleep(1000);
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
