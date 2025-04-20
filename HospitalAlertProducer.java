package com.hospital.producer;

import org.apache.kafka.clients.producer.*;
import java.util.Properties;

public class HospitalAlertProducer {
    public static void main(String[] args) {
        Properties props = KafkaConfig.getProducerProps();
        Producer<String, String> producer = new KafkaProducer<>(props);
        String topic = "hospital_alerts";

        for (int i = 1; i <= 5; i++) {
            String alert = String.format("{\"alertId\":\"A%03d\",\"type\":\"EMERGENCY\",\"message\":\"Fire drill in Ward-%d\",\"priority\":\"HIGH\"}",
                i, i);
            producer.send(new ProducerRecord<>(topic, alert));
            System.out.println("Produced: " + alert);
            try { Thread.sleep(4000); } catch (Exception e) { e.printStackTrace(); }
        }

        producer.close();
    }
}
