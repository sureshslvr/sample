package batchconsume;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;


public class ReceiveLogs {
	private static final String EXCHANGE_NAME = "logs";

	public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        channel.queueDeclare("queue-1", false, false, false, null);
        channel.queueBind("queue-1", EXCHANGE_NAME, "hi");
        Consumer batchConsumer = new BatchConsumer().getBatchConsumer(channel, "queue-1");
        channel.basicQos(5);
        String consumerTag = channel.basicConsume("queue-1", false, batchConsumer);
		channel.basicCancel(consumerTag);
		channel.close();
		connection.close();

    /*    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume("queue-1", true, deliverCallback, consumerTag -> { });
    }
    */
    
        
    }

}