package com.example.demo.service.impl;

import com.example.demo.event.FlightStatusEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    /**
     * Listens for messages on the "flight-status-updates" topic.
     * The groupId must match the one configured in application.properties.
     * Spring Kafka automatically deserializes the JSON message into a FlightStatusEvent object.
     * @param event The FlightStatusEvent received from Kafka.
     */
    @KafkaListener(topics = "flight-status-updates", groupId = "my-airport-app-group")
    public void listenFlightStatusUpdates(FlightStatusEvent event) {
        System.out.println("Notification Consumer received flight update: " + event);
        // Here you would implement logic to:
        // - Send push notifications to passengers
        // - Update airport display boards
        // - Log the event for analytics
        // - Trigger other business processes
        if ("DEPARTED".equals(event.getStatus())) {
            System.out.println("Sending departure notification for flight " + event.getFlightNumber());
        } else if ("DELAYED".equals(event.getStatus())) {
            System.out.println("Alerting staff about delayed flight " + event.getFlightNumber());
        }
    }

    // You can have multiple listeners for the same topic with different group IDs
    // to allow different services to process the same events independently.
    @KafkaListener(topics = "flight-status-updates", groupId = "baggage-tracking-group")
    public void listenForBaggageTracking(FlightStatusEvent event) {
        System.out.println("Baggage Tracking Consumer received update for flight: " + event.getFlightNumber() + " status: " + event.getStatus());
        // Logic for baggage handling system updates
    }
}