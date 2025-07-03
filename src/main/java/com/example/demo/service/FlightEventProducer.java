package com.example.demo.service;

import com.example.demo.event.FlightStatusEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FlightEventProducer {

    // Define the Kafka topic name
    private static final String TOPIC = "flight-status-updates";

    // KafkaTemplate is provided by Spring Kafka for sending messages
    @Autowired
    private KafkaTemplate<String, FlightStatusEvent> kafkaTemplate;

    /**
     * Sends a flight status update event to the Kafka topic.
     * @param event The FlightStatusEvent object to send.
     */
    public void sendFlightStatusUpdate(FlightStatusEvent event) {
        // The send method takes the topic, key, and value.
        // Using flightNumber as key ensures all messages for a specific flight
        // go to the same partition, maintaining order for that flight.
        kafkaTemplate.send(TOPIC, event.getFlightNumber(), event);
        System.out.println("Produced flight status update: " + event + " to topic " + TOPIC);
    }

    // Example method to demonstrate usage (e.g., called from a REST controller)
    public void simulateFlightUpdate(String flightNum, String status, String gate) {
        FlightStatusEvent event = new FlightStatusEvent(flightNum, status, System.currentTimeMillis(), gate);
        sendFlightStatusUpdate(event);
    }
}