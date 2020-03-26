import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class Agency {

    public static void main(String[] argv) throws Exception {
        // info
        System.out.println("Agency");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Type ID");
        String id = br.readLine();

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // exchange agency
        String EXCHANGE_AGENCY = "exchange_agency";
        channel.exchangeDeclare(EXCHANGE_AGENCY, BuiltinExchangeType.TOPIC);

        // exchange admin topic
        String EXCHANGE_ADMIN = "exchange_admin";
        channel.exchangeDeclare(EXCHANGE_ADMIN, BuiltinExchangeType.TOPIC);

        // exchange shipper direct
        String EXCHANGE_SHIPPER = "exchange_shipper";
        channel.exchangeDeclare(EXCHANGE_SHIPPER, BuiltinExchangeType.TOPIC);

        // admin queue & bind receiving
        String queueNameAdmin = channel.queueDeclare().getQueue();
        channel.queueBind(queueNameAdmin, EXCHANGE_ADMIN, "all.#");
        channel.queueBind(queueNameAdmin, EXCHANGE_ADMIN, "agency.#");
        channel.queueBind(queueNameAdmin, EXCHANGE_SHIPPER, "ack." + id + ".#");

        Consumer consumer_admin = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                handleAdminMessage(new String(body, "UTF-8"));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        channel.basicConsume(queueNameAdmin, false, consumer_admin);

        while (true) {
            // read msg
            System.out.println("Enter order: ");
            String []str_message = br.readLine().split("\\.");

            // KEY: (people/stuff/sat)
            String key = str_message[0];
            // MESSAGE: (all/shipper/agent).<to_whom>.<from_whom>.<date>.<message>
            String message = str_message[0] + ".unknown." + id + "." + new Date().toString() + "." + str_message[1];

            // break condition
            if ("exit".equals(str_message)) {
                break;
            }

            // publish
            channel.basicPublish(EXCHANGE_AGENCY, key, null, message.getBytes("UTF-8"));
            System.out.println("Sent: " + message + " key: " + key);
        }

    }


    static private void handleAdminMessage(String str_message) {
        String []message = str_message.split("\\.");
        if (message[0].equals("ack")) {
            System.out.println("\n==========Received ACK!===========");
        } else {
            System.out.println("\n=====Received ADMIN message!======");
        }
        System.out.println("Full message: " + str_message);
        System.out.println("Key: " + message[0]);
        System.out.println("To: " + message[1]);
        System.out.println("From: " + message[2]);
        System.out.println("Date: " + message[3]);
        if (message.length > 4) System.out.println("Message: " + message[4]);
        System.out.println("================================\n");
        System.out.println("Enter order: ");
    }
}
