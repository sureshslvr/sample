package Requeueingex;


import java.util.Scanner;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {
	private static final String EXCHANGE_NAME = "requeueexchange";

	public static void main(String[] args) throws Exception {
	ConnectionFactory cf=new ConnectionFactory();
	cf.setHost("localhost");
	Connection c=cf.newConnection();
	Channel channel = c.createChannel();

    channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
    //channel.queueDeclare("requeue-q1", true, false, false, null);
    channel.queueBind("requeue-q1", EXCHANGE_NAME, "hi");
    Scanner sc=new Scanner(System.in);
    System.out.println("enter a num to send msgs");
    int a=sc.nextInt();
    System.out.println("enter msg");
    String msg=sc.next();
    for(int i=1;i<=a;i++) {
    	 msg+=" "+i;
    	 channel.basicPublish(EXCHANGE_NAME, "hi", null, msg.getBytes());
    }
    sc.close();
    System.out.println("messages sent");
    
	

	}

}
