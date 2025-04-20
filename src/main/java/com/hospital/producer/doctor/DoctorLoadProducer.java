package com.hospital.producer.doctor;

import com.hospital.producer.config.KafkaConfig;
import org.apache.kafka.clients.producer.*;
import java.time.Instant;
import java.util.Properties;

public class DoctorLoadProducer {
    public static void main(String[] args) {
        Properties props = KafkaConfig.getProducerProps();
        Producer<String, String> producer = new KafkaProducer<>(props);

        String topic = "doctor_load";

        for (int i = 1; i <= 10; i++) {
            String json = String.format("{\"doctorId\":\"D%04d\",\"name\":\"Doctor %d\",\"patientCount\":%d,\"timestamp\":\"%s\"}",
                    i, i, (int)(Math.random() * 20), Instant.now().toString());

            ProducerRecord<String, String> record = new ProducerRecord<>(topic, json);
            producer.send(record);
            System.out.println("Produced: " + json);

            try { Thread.sleep(3000); } catch (Exception e) { e.printStackTrace(); }
        }

        producer.close();
    }
} 