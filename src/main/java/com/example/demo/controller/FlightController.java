package com.example.demo.controller;

import com.example.demo.event.FlightStatusEvent;
import com.example.demo.service.FlightEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    @Autowired
    private FlightEventProducer flightEventProducer;

    @PostMapping("/update-status")
    public ResponseEntity<String> updateFlightStatus(@RequestBody FlightStatusEvent event) {
        flightEventProducer.sendFlightStatusUpdate(event);
        return ResponseEntity.ok("Flight status update sent to Kafka for flight: " + event.getFlightNumber());
    }

    // Example endpoint to test
    @GetMapping("/test-delay/{flightNumber}")
    public ResponseEntity<String> testDelay(@PathVariable String flightNumber) {
        FlightStatusEvent event = new FlightStatusEvent(flightNumber, "DELAYED", System.currentTimeMillis(), "A12");
        flightEventProducer.sendFlightStatusUpdate(event);
        return ResponseEntity.ok("Simulated delay for flight: " + flightNumber);
    }
}