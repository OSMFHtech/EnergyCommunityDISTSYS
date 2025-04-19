package com.energy;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Random;

public class CommunityEnergyProducer {
    private final static String QUEUE_NAME = "energy";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest"); // replace "user" with valid RabbitMQ username
        factory.setPassword("guest"); // replace "password" with valid RabbitMQ password

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            Random random = new Random();

            while (true) {
                double kwh = 0.002 + (0.005 - 0.002) * random.nextDouble();
                String kwhStr = String.valueOf(kwh);
                String dateTimeStr = LocalDateTime.now().toString();
                String message = String.format(
                        "{\"type\":\"PRODUCER\",\"association\":\"COMMUNITY\",\"kwh\":%s,\"datetime\":\"%s\"}",
                        kwhStr, dateTimeStr
                );

                channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println("[PRODUCER] Sent: " + message);
                Thread.sleep(5000); // Every 5 seconds
            }
        }
    }
}