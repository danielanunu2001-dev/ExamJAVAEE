package com.voyageconnect.controller;

import com.voyageconnect.controller.dto.BookingRequest;
import com.voyageconnect.model.Booking;
import com.voyageconnect.model.BookingStatus;
import com.voyageconnect.model.Destination;
import com.voyageconnect.model.User;
import com.voyageconnect.repository.BookingRepository;
import com.voyageconnect.repository.DestinationRepository;
import com.voyageconnect.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final DestinationRepository destinationRepository;
    private final UserRepository userRepository;

    public BookingController(BookingRepository bookingRepository, DestinationRepository destinationRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.destinationRepository = destinationRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest req, Authentication auth) {
        String email = auth.getName();
        Optional<User> uo = userRepository.findByEmail(email);
        if (uo.isEmpty()) return ResponseEntity.status(401).build();

        Optional<Destination> doo = destinationRepository.findById(req.getDestinationId());
        if (doo.isEmpty()) return ResponseEntity.badRequest().body("Invalid destination");

        Booking b = Booking.builder()
                .user(uo.get())
                .destination(doo.get())
                .bookingDate(LocalDate.parse(req.getBookingDate()))
                .status(BookingStatus.PENDING)
                .build();

        Booking saved = bookingRepository.save(b);
        return ResponseEntity.ok(saved);
    }
}
