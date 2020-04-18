import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class Shipper {
    static String types[] = {"people", "stuff", "sat"};
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] argv) throws Exception {
        // info
        System.out.println("Shipper");

        // shipper id
        System.out.println("Type ID");
        String id = br.readLine();

        // shipper type
        int []shipper_types = getShipperTypes();
        System.out.println("Your choose: ");
        for (int type : shipper_types) {
            System.out.println("(" + type + ") " + types[type]);
        }

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // exchange agency topic
        String EXCHANGE_AGENCY = "exchange_agency";
        channel.exchangeDeclare(EXCHANGE_AGENCY, BuiltinExchangeType.TOPIC);

        // exchange admin topic
        String EXCHANGE_ADMIN = "exchange_admin";
        channel.exchangeDeclare(EXCHANGE_ADMIN, BuiltinExchangeType.TOPIC);

        // exchange shipper topic
        String EXCHANGE_SHIPPER = "exchange_shipper";
        channel.exchangeDeclare(EXCHANGE_SHIPPER, BuiltinExchangeType.TOPIC);

        // routing keys
        String []routingKeys = {types[shipper_types[0]] + ".#", types[shipper_types[1]] + ".#"};

        // agency queue & bind receiving
        Consumer consumer_agency = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String agent_id = handleAgencyMessage(new String(body, "UTF-8"));
                channel.basicAck(envelope.getDeliveryTag(), false);
                // ack.<to_whom>.<from_whom>.<date>.<message>
                String ack = "ack." + agent_id + "." + id + "." + new Date().toString() + ".Order completed!";
                channel.basicPublish(EXCHANGE_SHIPPER, "ack." + agent_id, null, ack.getBytes("UTF-8"));
            }
        };

        for (String routingKey: routingKeys) {
            String queueNameAgency = routingKey;
            channel.queueDeclare(queueNameAgency, false, false, false, null);
            channel.queueBind(queueNameAgency, EXCHANGE_AGENCY, routingKey);
            System.out.println("created queue: " + queueNameAgency + " routing key: " + routingKey);
            channel.basicConsume(queueNameAgency, false, consumer_agency);
        }

        // admin queue & bind
        String queueNameAdmin = channel.queueDeclare().getQueue();
        channel.queueBind(queueNameAdmin, EXCHANGE_ADMIN, "all.#");
        channel.queueBind(queueNameAdmin, EXCHANGE_ADMIN, "shipper.#");

        // qos
        channel.basicQos(1);

        // consumer (message from admin handling)
        Consumer consumer_admin = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                handleAdminMessage(new String(body, "UTF-8"));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        // start listening
        System.out.println("Waiting for messages...");
        channel.basicConsume(queueNameAdmin, false, consumer_admin);
    }

    static private void handleAdminMessage(String str_message) {
        String []message = str_message.split("\\.");
        System.out.println("\n=====Received ADMIN message!======");
        System.out.println("Full message: " + str_message);
        System.out.println("Key: " + message[0]);
        System.out.println("To: " + message[1]);
        System.out.println("From: " + message[2]);
        System.out.println("Date: " + message[3]);
        if (message.length > 4) System.out.println("Message: " + message[4]);
        System.out.println("================================\n");
    }

    static public String handleAgencyMessage(String str_message) {
        // MESSAGE: (people/stuff/sat).<agent_id>.<date>.<message>
        String []message = str_message.split("\\.");
        System.out.println("\n==========Received ORDER!==========");
        System.out.println("Full message: " + str_message);
        System.out.println("Key: " + message[0]);
        System.out.println("To: " + message[1]);
        System.out.println("From: " + message[2]);
        System.out.println("Date: " + message[3]);
        if (message.length > 4) System.out.println("Message: " + message[4]);
        System.out.print("Working on order");
        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(1000);
                System.out.print(".");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\nOrder completed! Ready for next one.");
        System.out.println("===================================\n");
        return message[2];
    }

    static private int validType(String type) throws IllegalArgumentException {
        int int_type = Integer.parseInt(type);
        if (int_type > 2 && int_type < 0) {
            throw new IllegalArgumentException("Shipper type must be digit between 0 and 2");
        }
        return int_type;
    }

    static private void validTypesCount(String []types) {
        if (types.length != 2) {
            throw new IllegalArgumentException("Shipper must have two services.");
        }
    }

    static private int[] parseStringToIntegerTypes(String []types) {
        validTypesCount(types);
        int[] int_types = new int[2];
        for (int i = 0; i < 2; i++) {
            int_types[i] = validType(types[i]);
        }
        return int_types;
    }

    static private int[] getShipperTypes() throws Exception {
        System.out.println("Type shipper types: ");
        System.out.println("(0) People");
        System.out.println("(1) Stuff");
        System.out.println("(2) Satellite");
        String []type_input = br.readLine().split(" ");
        return parseStringToIntegerTypes(type_input);
    }
}