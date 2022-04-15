package batchpublish;

import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class BatchSender {
	private static final String EXCHANGE_NAME = "batch_publish";

	public static void main(String[] args) {
		ConnectionFactory cf=new ConnectionFactory();
		cf.setHost("localhost");
		Connection c=cf.newConnection();
		Channel channel = c.createChannel();
		channel.confirmSelect();
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
	    channel.queueDeclare("queue-1", false, false, false, null);
	    channel.queueBind("queue-1", EXCHANGE_NAME, "hi");
	   
	    int batchSize = 100;
		int outstandingMessageCount = 0;
		while (thereAreMessagesToPublish()) {
		byte[] body = ...;
		BasicProperties properties = ...;
		channel.basicPublish(EXCHANGE_NAME, "hi", properties, body);
		outstandingMessageCount++;
		if (outstandingMessageCount == batchSize) {
		channel.waitForConfirmsOrDie(5_000);
		outstandingMessageCount = 0;
		}
		}
		if(outstandingMessageCount > 0) {
		channel.waitForConfirmsOrDie(5_000);
		}

	}

	private static boolean thereAreMessagesToPublish() {
		// TODO Auto-generated method stub
		return false;
	}

}
