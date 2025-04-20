package com.hospital.producer.bed;

import com.hospital.producer.config.KafkaConfig;
import org.apache.kafka.clients.producer.*;
import java.time.Instant;
import java.util.Properties;

public class BedStatusProducer {
    public static void main(String[] args) {
        Properties props = KafkaConfig.getProducerProps();
        Producer<String, String> producer = new KafkaProducer<>(props);

        String topic = "bed_status";

        for (int i = 1; i <= 10; i++) {
            String json = String.format("{\"bedId\":\"B%04d\",\"status\":\"%s\",\"patientId\":\"P%04d\",\"timestamp\":\"%s\"}",
                    i, Math.random() > 0.5 ? "Occupied" : "Available", i, Instant.now().toString());

            ProducerRecord<String, String> record = new ProducerRecord<>(topic, json);
            producer.send(record);
            System.out.println("Produced: " + json);

            try { Thread.sleep(3000); } catch (Exception e) { e.printStackTrace(); }
        }

        producer.close();
    }
} 