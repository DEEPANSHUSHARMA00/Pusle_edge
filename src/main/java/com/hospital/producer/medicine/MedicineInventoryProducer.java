package com.hospital.producer.medicine;

import com.hospital.producer.config.KafkaConfig;
import org.apache.kafka.clients.producer.*;
import java.time.Instant;
import java.util.Properties;

public class MedicineInventoryProducer {
    public static void main(String[] args) {
        Properties props = KafkaConfig.getProducerProps();
        Producer<String, String> producer = new KafkaProducer<>(props);

        String topic = "medicine_inventory";

        for (int i = 1; i <= 10; i++) {
            String json = String.format("{\"medicineId\":\"M%04d\",\"name\":\"Medicine %d\",\"quantity\":%d,\"lastUpdated\":\"%s\"}",
                    i, i, (int)(Math.random() * 100), Instant.now().toString());

            ProducerRecord<String, String> record = new ProducerRecord<>(topic, json);
            producer.send(record);
            System.out.println("Produced: " + json);

            try { Thread.sleep(3000); } catch (Exception e) { e.printStackTrace(); }
        }

        producer.close();
    }
} 