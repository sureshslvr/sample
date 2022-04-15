package Mqtt_test;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;

import batchconsume.BatchConsumer;


public class ReceivemsgsFromAmqpQueueBatchwise {

	public static void main(String[] args) throws Exception {
		
		// TODO Auto-generated method stub
		ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("mqtt_test", "fanout");
        channel.queueDeclare("mqtt_Queue", false, false, false, null);
        channel.queueBind("mqtt_Queue", "mqtt_test", "hi");
        Consumer batchConsumer = new BatchConsumer().getBatchConsumer(channel, "mqtt_Queue");
        channel.basicQos(5);
        String consumerTag = channel.basicConsume("mqtt_Queue", false, batchConsumer);
		channel.basicCancel(consumerTag);
		channel.close();
		connection.close();
	}

}
