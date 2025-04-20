package com.hospital.producer;

import org.apache.kafka.clients.producer.*;
import java.util.Properties;

public class DoctorLoadProducer {
    public static void main(String[] args) {
        Properties props = KafkaConfig.getProducerProps();
        Producer<String, String> producer = new KafkaProducer<>(props);
        String topic = "doctor_load";

        for (int i = 1; i <= 10; i++) {
            String json = String.format("{\"doctorId\":\"D%03d\",\"name\":\"Dr. %c\",\"patientsToday\":%d}",
                i, (char)('A' + i), (int)(Math.random() * 10 + 5));
            producer.send(new ProducerRecord<>(topic, json));
            System.out.println("Produced: " + json);
            try { Thread.sleep(2500); } catch (Exception e) { e.printStackTrace(); }
        }

        producer.close();
    }
}
