package com.hospital.producer.patient;

import com.hospital.producer.config.KafkaConfig;
import org.apache.kafka.clients.producer.*;
import java.time.Instant;
import java.util.Properties;

public class PatientCheckinProducer {
    public static void main(String[] args) {
        Properties props = KafkaConfig.getProducerProps();
        Producer<String, String> producer = new KafkaProducer<>(props);

        String topic = "patient_checkin";

        for (int i = 1; i <= 10; i++) {
            String json = String.format("{\"patientId\":\"P%04d\",\"name\":\"Patient %d\",\"department\":\"Cardiology\",\"checkInTime\":\"%s\",\"priority\":\"Medium\"}",
                    i, i, Instant.now().toString());

            ProducerRecord<String, String> record = new ProducerRecord<>(topic, json);
            producer.send(record);
            System.out.println("Produced: " + json);

            try { Thread.sleep(3000); } catch (Exception e) { e.printStackTrace(); }
        }

        producer.close();
    }
} 