package Requeueingex;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class BasicNackEX {
	
	private static final String EXCHANGE_NAME ="requeueexchange";

	public static void main(String[] args) throws Exception {
	ConnectionFactory cf=new ConnectionFactory();
	cf.setHost("localhost");
	Connection c=cf.newConnection();
	Channel channel = c.createChannel();
	channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
    //channel.queueDeclare("requeue-q1", false, false, false,null);
    channel.queueBind("requeue-q1", EXCHANGE_NAME, "hi");
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
        
            // requeue all unacknowledged deliveries up to
            // this delivery tag
           channel.basicNack(deliveryTag, true, false);
            //channel.basicNack(deliveryTag, true, false);
            //when requeue true it  requeue the messages as ready to consume
           //when requeue false it  simply discard the messages from the queue
           //requeue-q1 connencted with dead-letter-exchange=>demo-dead-letter-queue
   	    //if msg was rejected thern it redirected to demo-dead-letter-queue
            System.out.println("Read message : " + (new String(body)));
        }
    });

}}
