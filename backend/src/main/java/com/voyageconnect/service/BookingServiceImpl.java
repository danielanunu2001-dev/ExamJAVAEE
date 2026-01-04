package com.voyageconnect.service;

import com.voyageconnect.controller.dto.BookingRequest;
import com.voyageconnect.model.Booking;
import com.voyageconnect.model.BookingStatus;
import com.voyageconnect.model.Destination;
import com.voyageconnect.model.User;
import com.voyageconnect.repository.BookingRepository;
import com.voyageconnect.repository.DestinationRepository;
import com.voyageconnect.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final DestinationRepository destinationRepository;
    private final UserRepository userRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, DestinationRepository destinationRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.destinationRepository = destinationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Booking createBooking(BookingRequest req, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

        Destination destination = destinationRepository.findById(req.getDestinationId())
                .orElseThrow(() -> new RuntimeException("Destination not found with id: " + req.getDestinationId()));

        Booking b = Booking.builder()
                .user(user)
                .destination(destination)
                .bookingDate(LocalDate.parse(req.getBookingDate()))
                .status(BookingStatus.PENDING)
                .build();

        return bookingRepository.save(b);
    }
}
