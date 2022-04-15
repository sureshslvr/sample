package Mqtt_test;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SendingmsgstoMqtt {

	public static void main(String[] args) throws Exception {
		
		String topic = "amq.topic";
		//String brokerUrlRpi_Mqtt = "tcp://192.168.xxx.xxx:1883/";
		String clientId=UUID.randomUUID().toString();
		// TODO Auto-generated method stub
		String brokerUrlRpi_Mqtt = "tcp://localhost:1883";
		//String clientId="ExamplePublish";
		String channel = "SensorIntegratedData";
		int qos=0;

		public void publish(String data) throws MqttPersistenceException, MqttException {
		  String time = new Timestamp(System.currentTimeMillis()).toString();
		  System.out.println("Publishing at: "+time+ " to topic \""+channel+"\" qos "+qos);
		  MqttMessage message = new MqttMessage(data.getBytes());
		  message.setQos(qos);
		  message.setRetained(false);
		  client.publish(channel, message);

	}

}
