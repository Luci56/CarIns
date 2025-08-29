package com.example.carins.web.dto;

import java.time.LocalDate;

//am creat doar dto deoarece nue  o entitate sa o ounem in baza de date ea doar preia anumite date de acolo
public class CarHistoryDto {

    private LocalDate eventDate; // data evenimentului
    private String eventType;    // tipul evenimentului: "POLICY" sau "CLAIM"
    private String details;      // detalii despre eveniment (descriere, provider, suma etc.)

    // Constructor
    public CarHistoryDto(LocalDate eventDate, String eventType, String details) {
        this.eventDate = eventDate;
        this.eventType = eventType;
        this.details = details;
    }

    // Getters si setters
    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
}