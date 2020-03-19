import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.Arrays;

public class Server {

    public static void main(String[] args) throws IOException {

        System.out.println("JAVA TCP/UDP SERVER");
        final int serverPort = 9010;
        int client = 0;
        int maxUsers = 10;
        ServerSocket tcpServer = new ServerSocket(serverPort);
        Thread []users = new Thread[maxUsers];
        PrintWriter []out = new PrintWriter[maxUsers];
        BufferedReader []in = new BufferedReader[maxUsers];
        DatagramSocket udpSocket = new DatagramSocket(serverPort);
        
        Thread udpServer = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        byte[] receiveBuffer = new byte[1024];
                        Arrays.fill(receiveBuffer, (byte)0);
                        DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                        udpSocket.receive(receivePacket);
                        String msg = new String(receivePacket.getData());
                        int userId = Integer.parseInt(String.valueOf(msg.charAt(1)));
                        
                        System.out.print("RECEIVED(UDP): " + msg + ", SEND TO: ");
                        for (int i = 0; i < maxUsers; i++) {
                            if (out[i] != null && i != userId) {
                                out[i].println(msg);
                                System.out.print(i + ", ");
                            }
                        }
                        System.out.print("\n");
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                }
            }
        });
        udpServer.start();

        while (client < maxUsers) {
            final int id = client;
            final Socket clientSocket = tcpServer.accept();
            System.out.println("CLIENT " + id + " tcp:" + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " CONNECTED");
            
            users[id] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        out[id] = new PrintWriter(clientSocket.getOutputStream(), true);
                        in[id] = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                        out[id].println(id);

                        while (true) {
                            String msg = in[id].readLine();
                            if (msg.equals("q")) {
                                System.out.println("CLIENT " + id + " DISCONNECTED");
                                out[id] = null;
                                in[id] = null;
                                break;
                            }
                            if (msg != null) {
                                System.out.print("RECEIVED(TCP" + id + "): " + msg + ", SEND TO: ");
                                for (int i = 0; i < maxUsers; i++) {
                                    if (i != id && out[i] != null) {
                                        out[i].println(msg);
                                        System.out.print(i + ", ");
                                    }
                                }
                                System.out.print("\n");
                            }
                        }
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                }
            });
            users[client].start();
            client++;
        }
        if (tcpServer != null) {
            tcpServer.close();
        }
        if (udpSocket != null) {
            udpSocket.close();
        }
    }
}
