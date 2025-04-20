package com.hospital.producer.alert;

import com.hospital.producer.config.KafkaConfig;
import org.apache.kafka.clients.producer.*;
import java.time.Instant;
import java.util.Properties;

public class HospitalAlertProducer {
    public static void main(String[] args) {
        Properties props = KafkaConfig.getProducerProps();
        Producer<String, String> producer = new KafkaProducer<>(props);

        String topic = "hospital_alerts";

        for (int i = 1; i <= 10; i++) {
            String json = String.format("{\"alertId\":\"A%04d\",\"type\":\"Emergency\",\"message\":\"Alert %d\",\"timestamp\":\"%s\"}",
                    i, i, Instant.now().toString());

            ProducerRecord<String, String> record = new ProducerRecord<>(topic, json);
            producer.send(record);
            System.out.println("Produced: " + json);

            try { Thread.sleep(3000); } catch (Exception e) { e.printStackTrace(); }
        }

        producer.close();
    }
} 