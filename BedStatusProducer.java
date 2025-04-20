package com.hospital.producer;

import org.apache.kafka.clients.producer.*;
import java.util.Properties;
import java.util.Random;

public class BedStatusProducer {
    public static void main(String[] args) {
        Properties props = KafkaConfig.getProducerProps();
        Producer<String, String> producer = new KafkaProducer<>(props);
        String topic = "bed_status";

        String[] statuses = {"Available", "Occupied", "Under Maintenance"};
        Random rand = new Random();

        for (int i = 1; i <= 10; i++) {
            String json = String.format("{\"bedId\":\"B%03d\",\"ward\":\"Ward-%d\",\"status\":\"%s\"}",
                i, (i % 3) + 1, statuses[rand.nextInt(statuses.length)]);
            producer.send(new ProducerRecord<>(topic, json));
            System.out.println("Produced: " + json);

            try { Thread.sleep(2000); } catch (Exception e) { e.printStackTrace(); }
        }

        producer.close();
    }
}
