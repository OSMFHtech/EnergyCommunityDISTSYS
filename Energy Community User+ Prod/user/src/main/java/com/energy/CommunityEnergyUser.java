package com.energy;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Random;

public class CommunityEnergyUser {
    private final static String QUEUE_NAME = "energy";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            Random random = new Random();

            while (true) {
                double kwh = 0.001 + (0.003 - 0.001) * random.nextDouble();
                String kwhStr = String.valueOf(kwh);
                String dateTimeStr = LocalDateTime.now().toString();
                String message = String.format(
                        "{\"type\":\"USER\",\"association\":\"COMMUNITY\",\"kwh\":%s,\"datetime\":\"%s\"}",
                        kwhStr, dateTimeStr
                );

                channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println("[USER] Sent: " + message);
                Thread.sleep(5000); // Every 5 seconds
            }
        }
    }
}