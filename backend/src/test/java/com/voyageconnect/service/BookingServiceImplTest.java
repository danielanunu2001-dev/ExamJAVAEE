package com.voyageconnect.service;

import com.voyageconnect.controller.dto.BookingRequest;
import com.voyageconnect.model.Booking;
import com.voyageconnect.model.Destination;
import com.voyageconnect.model.User;
import com.voyageconnect.repository.BookingRepository;
import com.voyageconnect.repository.DestinationRepository;
import com.voyageconnect.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private DestinationRepository destinationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    void createBooking_shouldCreateBooking_whenUserAndDestinationExist() {
        BookingRequest request = new BookingRequest(1L, "2025-01-01");
        User user = new User();
        user.setEmail("user@example.com");
        Destination destination = new Destination();
        destination.setId(1L);

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(destinationRepository.findById(1L)).thenReturn(Optional.of(destination));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(i -> i.getArguments()[0]);

        Booking result = bookingService.createBooking(request, "user@example.com");

        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals(destination, result.getDestination());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void createBooking_shouldThrowException_whenUserNotFound() {
        BookingRequest request = new BookingRequest(1L, "2025-01-01");
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookingService.createBooking(request, "user@example.com"));

        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    void createBooking_shouldThrowException_whenDestinationNotFound() {
        BookingRequest request = new BookingRequest(1L, "2025-01-01");
        User user = new User();
        user.setEmail("user@example.com");
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(destinationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookingService.createBooking(request, "user@example.com"));

        verify(bookingRepository, never()).save(any(Booking.class));
    }
}
