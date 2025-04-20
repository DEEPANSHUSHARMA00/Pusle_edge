package com.hospital.producer;

import org.apache.kafka.clients.producer.*;
import java.util.Properties;
import java.util.Random;

public class MedicineInventoryProducer {
    public static void main(String[] args) {
        Properties props = KafkaConfig.getProducerProps();
        Producer<String, String> producer = new KafkaProducer<>(props);
        String topic = "medicine_inventory";

        String[] medicines = {"Paracetamol", "Ibuprofen", "Aspirin"};
        Random rand = new Random();

        for (int i = 1; i <= 10; i++) {
            String json = String.format("{\"medicineId\":\"M%03d\",\"name\":\"%s\",\"quantity\":%d}",
                i, medicines[rand.nextInt(medicines.length)], rand.nextInt(50) + 10);
            producer.send(new ProducerRecord<>(topic, json));
            System.out.println("Produced: " + json);
            try { Thread.sleep(2000); } catch (Exception e) { e.printStackTrace(); }
        }

        producer.close();
    }
}
