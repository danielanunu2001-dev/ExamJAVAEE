package com.voyageconnect.controller.dto;

import lombok.Data;

@Data
public class BookingRequest {
    private Long destinationId;
    private String bookingDate; // ISO date YYYY-MM-DD
}
