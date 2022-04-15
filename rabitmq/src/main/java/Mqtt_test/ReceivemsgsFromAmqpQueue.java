package Mqtt_test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ReceivemsgsFromAmqpQueue {
	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();



		channel.exchangeDeclare("mqtt_test", "fanout");
		channel.queueDeclare("mqtt_Queue", false, false, false, null);
		//String queueName = channel.queueDeclare().getQueue();
		channel.queueBind("mqtt_Queue", "mqtt_test", "");



		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");



		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
		String message = new String(delivery.getBody(), "UTF-8");
		System.out.println(" [x] Received '" + message + "'");
		};
		channel.basicConsume("mqtt_Queue", true, deliverCallback, consumerTag -> { });
		}

}
