package autoconnectionrecovery;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnRecEx1 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection  connection=factory.newConnection();
		
		// Timeout for connection establishment: 5s
		factory.setConnectionTimeout( 5000 );
		// Configure automatic reconnections
		factory.setAutomaticRecoveryEnabled( true );
		 
		// Recovery interval: 10s
		factory.setNetworkRecoveryInterval( 10000 );
		 
		// Exchanges and so on should be redeclared if necessary
		factory.setTopologyRecoveryEnabled( true );
		
		
		Channel channel = connection.createChannel();
		channel.exchangeDeclare("ConnRecEx", "fanout");
	    channel.queueDeclare("connrec_queue", false, false, false, null);
	    channel.queueBind("connrec_queue", "ConnRecEx", "hi");
	}

}
