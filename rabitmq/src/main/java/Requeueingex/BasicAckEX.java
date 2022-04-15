package Requeueingex;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import batchconsume.BatchConsumer;

public class BasicAckEX {
	
	//basic ack false means msg should not be delated from queue
	//basic ack true means msg should  be delated from queue

	private static final String EXCHANGE_NAME ="requeueexchange";

	public static void main(String[] args) throws Exception {
	ConnectionFactory cf=new ConnectionFactory();
	cf.setHost("localhost");
	Connection c=cf.newConnection();
	Channel channel = c.createChannel();
	channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
    //channel.queueDeclare("requeue-q1", false, false, false, null);
    channel.queueBind("requeue-q1", EXCHANGE_NAME, "hi");


    /*Consumer batchConsumer = new BatchConsumer().getBatchConsumer(channel, "queue-1");
    channel.basicQos(1);*/
    boolean autoAck = false;
    String consumerTag = channel.basicConsume("requeue-q1", autoAck,"a-consumer-tag",
   	     new DefaultConsumer(channel) {
        @Override
        public void handleDelivery(String consumerTag,
                                   Envelope envelope,
                                   AMQP.BasicProperties properties,
                                   byte[] body)
            throws IOException
        {
            long deliveryTag = envelope.getDeliveryTag();
            // positively acknowledge a single delivery, the message will
            // be discarded
            
            //channel.basicAck(deliveryTag, false);
            channel.basicAck(deliveryTag, true);
            System.out.println("Read message : " + (new String(body)));
        }
    });
   

   
	}

}
