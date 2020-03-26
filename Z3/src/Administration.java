import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class Administration {
    public static void main(String[] argv) throws Exception {
        // info
        System.out.println("Administration");

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

        // agency queue & bind receving
        // add extra queue on agency exchange taking all messages sent by shipper and agency
        String queueNameAdmin = channel.queueDeclare().getQueue();
        channel.queueBind(queueNameAdmin, EXCHANGE_AGENCY, "#");
        channel.queueBind(queueNameAdmin, EXCHANGE_ADMIN, "#");
        channel.queueBind(queueNameAdmin, EXCHANGE_SHIPPER, "#");

        Consumer consumer_admin = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                handleMessage(new String(body, "UTF-8"));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        channel.basicConsume(queueNameAdmin, false, consumer_admin);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            // read msg
            System.out.println("Enter message(all/agency/shipper.<message>): ");
            String message = br.readLine();
            String key = message.split("\\.")[0];
            // MESSAGE: (all/shipper/agent).<to_whom>.<from_whom>.<date>.<message>
            String message_with_date = key + "." + key + ".admin." + new Date().toString() + "." + message.split("\\.")[1];

            // break condition
            if ("exit".equals(message)) {
                break;
            }

            // publish
            channel.basicPublish(EXCHANGE_ADMIN, key, null, message_with_date.getBytes("UTF-8"));
            System.out.println("Sent: " + message + " key: " + key);
        }
    }

    static void handleMessage(String str_message) {
        String []message = str_message.split("\\.");
        System.out.println("\n===========MESSAGE CATCHED=========");
        System.out.println("Full message: " + str_message);
        System.out.println("Key: " + message[0]);
        System.out.println("To: " + message[1]);
        System.out.println("From: " + message[2]);
        System.out.println("Date: " + message[3]);
        if (message.length > 4) System.out.println("Message: " + message[4]);
        System.out.println("===================================\n\n");
        System.out.println("Enter message(all/agency/shipper.<message>): ");
    }

}
