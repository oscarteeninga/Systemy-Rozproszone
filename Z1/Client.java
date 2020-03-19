import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        final String hostName = "localhost";
        final int portNumber = 9010;
        if (args.length == 0) {
            System.out.println("Too few arguments");
            return;
        }
        int groupNumber = Integer.parseInt(args[0]);
        final int multicastPort = portNumber + groupNumber;
        final String multicastAddress = "230.0.0." + args[0];
        Socket tcpSocket = new Socket(hostName, portNumber);;
        DatagramSocket udpSocket = new DatagramSocket();
        MulticastSocket multicastSocket = new MulticastSocket(multicastPort);
        InetAddress group = InetAddress.getByName(multicastAddress);

        try {
            InetAddress address = InetAddress.getByName(hostName);
            PrintWriter out = new PrintWriter(tcpSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));

            final int id = Integer.parseInt(in.readLine());
            System.out.println("CLIENT " + id + " GROUP " + groupNumber);
            System.out.println("TCP UNICAST: " + tcpSocket.getLocalSocketAddress());

            Thread tcpReceiving = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String msg = null;
                        while(true) {
                            msg = in.readLine();
                            if (msg != null) {
                                System.out.println(msg);
                            }
                        }
                    } catch (IOException e) {
                        //e.printStackTrace();
                    }
                }
            });
            tcpReceiving.start();

            Thread udpMulticastServer = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("UDP MULTICAST: " + multicastAddress + ":" + multicastSocket.getLocalPort());
                    try {
                        multicastSocket.joinGroup(group);
                        while (true) {
                            byte[] receiveBuffer = new byte[1024];
                            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                            multicastSocket.receive(receivePacket);
                            String msg = new String(receivePacket.getData());
                            int senderId = Integer.parseInt(String.valueOf(msg.charAt(3)));
                            if (senderId != id) System.out.println(msg);
                        }
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                }
            });
            udpMulticastServer.start();

            Scanner sc = new Scanner(System.in);
            while(true) {
                String msg = sc.nextLine();
                if (msg.equals("q")) {
                    System.out.println("EXIT...");
                    out.println(msg);
                    break;
                } else if (msg.equals("t")) {
                    String snd = sc.nextLine();
                    snd = "T" + id + ": " + snd;
                    out.println(snd);
                } else if (msg.equals("u")) {
                    byte[] sendBuffer = new byte[1024];
                    String snd = sc.nextLine();
                    snd = "U" + id + ": " + snd;
                    sendBuffer = snd.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
                    udpSocket.send(sendPacket);
                } else if (msg.equals("m")) {
                    String snd = sc.nextLine();
                    byte[] sendBuffer = new byte[1024];
                    snd = "G" + (multicastPort - portNumber) + "/" + id + ": " + snd;
                    sendBuffer = snd.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, group, multicastPort);
                    multicastSocket.send(sendPacket);
                } else if (msg.equals("a")) {
                    byte[] sendBuffer = new byte[1024];
                    String snd = "U" + id + ": " + getAsciiArt();
                    sendBuffer = snd.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
                    udpSocket.send(sendPacket);
                } else {
                    System.out.println("UNKNOWN COMMAND");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (tcpSocket != null){
                tcpSocket.close();
            }
            if (udpSocket != null) {
                udpSocket.close();
            }
            if (multicastSocket != null) {
                multicastSocket.close();
            }
        }
    }

    public static String getAsciiArt() {
        String sb = "\n";
        sb += "   _   \n";
        sb += " _(\")_ \n";
        sb += "(_ . _)\n";
        sb += " / : \\ \n";
        sb += "(_/ \\_)\n";
        return sb;
    }
}
