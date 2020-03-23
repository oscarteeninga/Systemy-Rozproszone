import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Z2_Producer {

    public static void main(String[] argv) throws Exception {

        // info
        System.out.println("Z2 PRODUCER");

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // exchange direct
        String EXCHANGE_DIRECT = "exchange1";
        channel.exchangeDeclare(EXCHANGE_DIRECT, BuiltinExchangeType.DIRECT);

        System.out.println("DIRECT EXCHANGE");
        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            // routing key
            System.out.println("Enter key: ");
            String routingKey = br.readLine();

            // read msg
            System.out.println("Enter message: ");
            String message = br.readLine();

            // break condition
            if ("exit".equals(message)) {
                break;
            }

            // publish
            channel.basicPublish(EXCHANGE_DIRECT, routingKey, null, message.getBytes("UTF-8"));
            System.out.println("Sent: " + message + " key: " + routingKey);
        }

        // exchange topic

        String EXCHANGE_TOPIC = "exchange2";
        channel.exchangeDeclare(EXCHANGE_TOPIC, BuiltinExchangeType.TOPIC);

        System.out.println("TOPIC EXCHANGE");
        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            // routing key
            System.out.println("Enter key: ");
            String routingKey = br.readLine();

            // read msg
            System.out.println("Enter message: ");
            String message = br.readLine();

            // break condition
            if ("exit".equals(message)) {
                break;
            }

            // publish
            channel.basicPublish(EXCHANGE_TOPIC, routingKey, null, message.getBytes("UTF-8"));
            System.out.println("Sent: " + message + " key: " + routingKey);
        }
    }
}
