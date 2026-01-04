package com.voyageconnect.service.interfaces;

import com.voyageconnect.controller.dto.BookingRequest;
import com.voyageconnect.model.Booking;

public interface BookingService {
    Booking createBooking(BookingRequest request, String userEmail);
}
