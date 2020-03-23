import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Z1_Producer_b {

    public static void main(String[] argv) throws Exception {

        // info
        System.out.println("Z1 PRODUCER");

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // queue
        String QUEUE_NAME = "queue1";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);        

        // producer (publish msg)
        String message = "";

        int []tab = {1, 5, 1, 5, 1, 5, 1, 5, 1, 5};
        for (int i = 0; i < tab.length; i++) {
            message = String.valueOf(tab[i]);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("Sent: " + tab[i]);
        }

        while (!message.equals("q")) {
            message = br.readLine();
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("Sent: " + message);

        }

        // close
        channel.close();
        connection.close();
    }
}
