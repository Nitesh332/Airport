package com.example.demo.event;


public class FlightStatusEvent {
    private String flightNumber;
    private String status;
    private long timestamp;
    private String gate;

    // Constructors
    public FlightStatusEvent() {}

    public FlightStatusEvent(String flightNumber, String status, long timestamp, String gate) {
        this.flightNumber = flightNumber;
        this.status = status;
        this.timestamp = timestamp;
        this.gate = gate;
    }

    // Getters and Setters (REQUIRED for JSON serialization/deserialization)
    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    public String getGate() { return gate; }
    public void setGate(String gate) { this.gate = gate; }

    @Override
    public String toString() {
        return "FlightStatusEvent{" +
                "flightNumber='" + flightNumber + '\'' +
                ", status='" + status + '\'' +
                ", timestamp=" + timestamp +
                ", gate='" + gate + '\'' +
                '}';
    }
}